package info.alkor.fuelusagecalculator.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import info.alkor.fuelusagecalculator.model.Entry;
import info.alkor.fuelusagecalculator.storage.PersistenceManager;

/**
 * Created by Marlena on 2017-02-08.
 */
public class EntrySwipeCallback extends ItemTouchHelper.SimpleCallback {

	private final RecyclerView recyclerView;

	public EntrySwipeCallback(RecyclerView view) {
		super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper
				.RIGHT);
		this.recyclerView = view;
	}

	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
	                      RecyclerView.ViewHolder target) {
		// order in list is automatically kept based on odometer
		return false;
	}

	@Override
	public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
		EntryViewHolder entryViewHolder = (EntryViewHolder) viewHolder;
		final EntryAdapter adapter = (EntryAdapter) recyclerView.getAdapter();
		adapter.deleteRequested(entryViewHolder);
	}
}
