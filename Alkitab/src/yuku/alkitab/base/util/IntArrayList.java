package yuku.alkitab.base.util;

import android.os.Parcel;
import android.os.Parcelable;

public class IntArrayList implements Parcelable {
	int[] buf;
	int len;

	public IntArrayList() {
		this(16);
	}

	public IntArrayList(int capacity) {
		buf = new int[capacity];
		this.len = 0;
	}

	public int size() {
		return this.len;
	}

	private void expand() {
		int[] newArray = new int[this.buf.length << 1];
		System.arraycopy(this.buf, 0, newArray, 0, this.len);
		this.buf = newArray;
	}

	public void add(int a) {
		if (this.len >= this.buf.length) {
			expand();
		}

		this.buf[this.len++] = a;
	}

	public int pop() {
		return this.buf[--this.len];
	}

	public int get(int i) {
		return this.buf[i];
	}

	public void set(int i, int a) {
		this.buf[i] = a;
	}

	/**
	 * DANGEROUS. Do not mess with this buffer carelessly.
	 * Use this for faster access to the underlying buffer only.
	 * The length of the returned array will be the same or larger than {@link #size()}.
	 */
	public int[] buffer() {
		return buf;
	}
	
	public void clear() {
		this.len = 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(len);
		for (int i = 0; i < len; i++) {
			dest.writeInt(buf[i]);
		}
	}

	public static final Parcelable.Creator<IntArrayList> CREATOR = new Parcelable.Creator<IntArrayList>() {
		public IntArrayList createFromParcel(Parcel in) {
			int len = in.readInt();
			
			IntArrayList res = new IntArrayList();
			res.buf = new int[len];
			for (int i = 0; i < len; i++) {
				res.buf[i] = in.readInt();
			}
			res.len = len;
			return res;
		}

		public IntArrayList[] newArray(int size) {
			return new IntArrayList[size];
		}
	};

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.len * 8);
		sb.append('[');
		for (int i = 0; i < len; i++) {
			sb.append(buf[i]);
			if (i != this.len - 1) {
				sb.append(", "); //$NON-NLS-1$
			}
		}
		sb.append(']');
		return sb.toString();
	}
}
