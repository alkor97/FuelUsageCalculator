package info.alkor.fuelusagecalculator.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Iterator;

/**
 * Created by Marlena on 2017-02-07.
 */
public class Optional<E> implements Iterable<E> {

	private final E value;

	private Optional(@Nullable E value) {
		this.value = value;
	}

	private static final class ValueIterator<E> implements Iterator<E> {

		private E value;

		public ValueIterator(@Nullable E value) {
			this.value = value;
		}

		@Override
		public boolean hasNext() {
			return value != null;
		}

		@Override
		public E next() {
			try {
				if (value == null) {
					throw new UnsupportedOperationException();
				}
				return value;
			} finally {
				value = null;
			}
		}
	}

	@Override
	@NonNull
	public Iterator<E> iterator() {
		return new ValueIterator<>(value);
	}

	public static <E> Optional<E> ofNullable(@Nullable E value) {
		return new Optional<>(value);
	}
}
