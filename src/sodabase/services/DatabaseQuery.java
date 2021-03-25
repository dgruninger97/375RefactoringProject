package sodabase.services;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseQuery<T> {
	public List<T> execute();
}
