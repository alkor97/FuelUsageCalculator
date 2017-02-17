package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.alkor.fuelusagecalculator.R;
import info.alkor.fuelusagecalculator.calculators.FuelUsageCalculator;
import info.alkor.fuelusagecalculator.model.Entry;
import info.alkor.fuelusagecalculator.model.EntryHelper;
import info.alkor.fuelusagecalculator.storage.PersistenceManager;

/**
 * Created by Marlena on 2016-11-15.
 */
public class EntryAdapter extends RecyclerView.Adapter<EntryViewHolder> {

	private static final int NORMAL_ENTRY = 0;
	private static final int PENDING_DELETE_ENTRY = 1;
	private static final int NO_PENDING_DELETE = -1;

	@NonNull
	private final List<Entry> entries;
	private final EntryHelper helper = new EntryHelper();
	@NonNull
	private final FuelUsageCalculator calculator;
	@NonNull
	private final OdometerAdapter odometerAdapter;
	private final DateAdapter dateAdapter = new DateAdapter();
	@NonNull
	private final VolumeAdapter volumeAdapter;
	@NonNull
	private final FuelUsageAdapter fuelUsageAdapter;
	private int pendingDelete = NO_PENDING_DELETE;
	private final PersistenceManager persistenceManager;
	private EntryLongClickObserver longClickObserver;

	public EntryAdapter(@NonNull List<Entry> entries, @NonNull FuelUsageCalculator calculator,
	                    PersistenceManager persistenceManager) {
		this.entries = entries;
		this.calculator = calculator;
		this.odometerAdapter = new OdometerAdapter(calculator.getOdometerUnit());
		this.volumeAdapter = new VolumeAdapter(calculator.getVolumeUnit());
		this.fuelUsageAdapter = new FuelUsageAdapter(calculator.getResultUnit());
		this.persistenceManager = persistenceManager;
	}

	public void addAll(@NonNull List<Entry> entries) {
		for (Entry entry : entries) {
			helper.insert(this.entries, entry, calculator);
		}
		notifyDataSetChanged();
	}

	public void add(@NonNull Entry entry) {
		resetPendingDelete();
		final int at = helper.insert(entries, entry, calculator);
		notifyItemInserted(at);
		int changedCount = entries.size() - at;
		if (changedCount > 0) {
			notifyItemChanged(1 + at);
		}
	}

	public void remove(Entry entry) {
		remove(entry, false);
	}

	public void remove(Entry entry, boolean save) {
		resetPendingDelete();
		final int position = helper.remove(entries, entry, calculator);
		if (position > -1) {
			notifyItemRemoved(position);
			notifyItemChanged(position);
			if (save) {
				persistenceManager.saveEntries();
			}
		}
	}

	@NonNull
	@Override
	public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main,
				parent, false);
		if (NORMAL_ENTRY == viewType) {
			itemView.findViewById(R.id.pendingDeleteView).setVisibility(View.INVISIBLE);
			itemView.findViewById(R.id.normalView).setVisibility(View.VISIBLE);
			return new NormalEntryViewHolder(itemView, dateAdapter, odometerAdapter,
					volumeAdapter, fuelUsageAdapter, longClickObserver);
		} else {
			itemView.findViewById(R.id.normalView).setVisibility(View.INVISIBLE);
			itemView.findViewById(R.id.pendingDeleteView).setVisibility(View.VISIBLE);
			return new PendingDeleteEntryViewHolder(itemView, this);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (pendingDelete == position) {
			return PENDING_DELETE_ENTRY;
		}
		return NORMAL_ENTRY;
	}

	@Override
	public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
		final Entry entry = entries.get(position);
		holder.fillWith(entry);
	}

	@Override
	public int getItemCount() {
		return entries.size();
	}

	@NonNull
	public DateAdapter getDateAdapter() {
		return dateAdapter;
	}

	@NonNull
	public OdometerAdapter getOdometerAdapter() {
		return odometerAdapter;
	}

	@NonNull
	public VolumeAdapter getVolumeAdapter() {
		return volumeAdapter;
	}

	public void setLongClickObserver(EntryLongClickObserver longClickObserver) {
		this.longClickObserver = longClickObserver;
	}

	public void deleteRequested(EntryViewHolder viewHolder) {
		final int position = viewHolder.getAdapterPosition();
		if (viewHolder.isDeletable()) {
			setPendingDelete(position);
		} else {
			resetPendingDelete();
		}
	}

	public void deleteConfirmed(EntryViewHolder viewHolder) {
		remove(viewHolder.getEntry(), true);
	}

	private void setPendingDelete(int newPendingDelete) {
		if (pendingDelete != NO_PENDING_DELETE) {
			notifyItemChanged(pendingDelete);
		}
		pendingDelete = newPendingDelete;
		if (pendingDelete != NO_PENDING_DELETE) {
			notifyItemChanged(pendingDelete);
		}
	}

	private void resetPendingDelete() {
		setPendingDelete(NO_PENDING_DELETE);
	}
}
