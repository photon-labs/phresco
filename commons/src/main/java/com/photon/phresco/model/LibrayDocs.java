package com.photon.phresco.model;

public class LibrayDocs {
	private int id;
	private int libraryid;

	public LibrayDocs () {

	}
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LibrayDocs [id=" + id + ", libraryid=" + libraryid + "]";
	}
	public LibrayDocs(int id, int libraryid) {
		super();
		this.id = id;
		this.libraryid = libraryid;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the libraryid
	 */
	public int getLibraryid() {
		return libraryid;
	}
	/**
	 * @param libraryid the libraryid to set
	 */
	public void setLibraryid(int libraryid) {
		this.libraryid = libraryid;
	}

}
