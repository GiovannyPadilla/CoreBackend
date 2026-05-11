package financetrack.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Integer id) {
        super(resource + " con id " + id + " no encontrado");
    }
}
