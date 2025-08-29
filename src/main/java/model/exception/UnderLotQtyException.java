package model.exception;

public class UnderLotQtyException extends RuntimeException {
	public UnderLotQtyException(int id ,int lot) {
		super(id + "のロット数は" + lot + "です");
	}
}
