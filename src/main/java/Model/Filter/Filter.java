package Model.Filter;

import java.util.ArrayList;

public interface Filter<T> {
    public abstract ArrayList<T> getListOfFilteredItems(String filter);
}
