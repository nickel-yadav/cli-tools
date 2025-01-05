import java.time.LocalDateTime;

public class Task {
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

   public Status getStatus() {
        return status;
   }

   public void setStatus(Status status) {
        this.status = status;
   }

   public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
   }

}
