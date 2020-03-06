package fr.excilys.computer_database.model;

public class Paginer {

	private String orderBy;
	private String columnName;
	private String search;
	private int offset;
	private int step;
	
	public Paginer(PaginerBuilder builder) {
		this.orderBy = builder.orderBy;
		this.columnName = builder.columnName;
		this.search = builder.search;
		this.offset = builder.offset;
		this.step = builder.step;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	
	
	public static class PaginerBuilder {
		
		private String orderBy;
		private String columnName;
		private String search;
		private int offset;
		private int step;

		public PaginerBuilder() {
		}

		public PaginerBuilder setOrderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}
		
		public PaginerBuilder setColumnName(String columnName) {
			this.columnName = columnName;
			return this;
		}

		public PaginerBuilder setSearch(String search) {
			this.search = search;
			return this;
		}

		public PaginerBuilder setOffset(int offset) {
			this.offset = offset;
			return this;
		}
		
		public PaginerBuilder setStep(int step) {
			this.step = step;
			return this;
		}

		public Paginer build() {
			return new Paginer(this);
		}
	}

	
	
}
