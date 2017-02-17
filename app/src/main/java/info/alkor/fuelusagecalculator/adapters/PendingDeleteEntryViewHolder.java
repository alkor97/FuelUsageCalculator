package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import info.alkor.fuelusagecalculator.R;
import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-09.
 */
public class PendingDeleteEntryViewHolder extends EntryViewHolder {

	private final EntryAdapter entryAdapter;
	private final Button confirmDelete;

	public PendingDeleteEntryViewHolder(@NonNull View view, EntryAdapter entryAdapter) {
		super(view, false);
		this.entryAdapter = entryAdapter;
		this.confirmDelete = (Button) view.findViewById(R.id.confirmDelete);
	}

	public void fillWith(final Entry entry) {
		super.fillWith(entry);
		confirmDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				entryAdapter.deleteConfirmed(PendingDeleteEntryViewHolder.this);
			}
		});
	}
}
