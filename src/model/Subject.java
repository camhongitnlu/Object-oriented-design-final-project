package model;

import view.SetCharacterObserver;

public interface Subject {
	public void register(SetCharacterObserver obs);

	public void remove(SetCharacterObserver obs);

	public void notifyToObserver();
}
