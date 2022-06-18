import java.util.List;

public interface DAO<T> {
	public T get(String id);
    
    public List<T> getAll();
    
    public void insert(T element);
    
    public void delete(T element);
}