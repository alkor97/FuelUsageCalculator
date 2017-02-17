package info.alkor.fuelusagecalculator.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.alkor.fuelusagecalculator.model.Entry;

/**
 * Created by Marlena on 2017-02-09.
 */
public class EntryViewHolder extends RecyclerView.ViewHolder {
	protected View view;
	protected Entry entry;
	private final boolean deletable;

	public EntryViewHolder(@NonNull View view, boolean deletable) {
		super(view);
		this.view = view;
		this.deletable = deletable;
	}

	public void fillWith(Entry entry) {
		this.entry = entry;
	}

	public Entry getEntry() {
		return entry;
	}

	public boolean isDeletable() {
		return deletable;
	}
}
