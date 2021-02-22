package myapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;
import java.util.List;

public class Release {

    private final int id;
    private final int employeeId;
    private final ReleaseStatus status;
    private final List<ProductRelease> productsRelease;
    @SerializedName("creationDate")
    private final ZonedDateTime creationDateTime;
    @SerializedName("realizationDate")
    private final ZonedDateTime realizationDateTime;

    public Release(int id, int employeeId, ReleaseStatus status, List<ProductRelease> productsRelease,
                   ZonedDateTime creationDateTime, ZonedDateTime realizationDateTime) {
        this.id = id;
        this.employeeId = employeeId;
        this.status = status;
        this.productsRelease = productsRelease;
        this.creationDateTime = creationDateTime;
        this.realizationDateTime = realizationDateTime;
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public ReleaseStatus getStatus() {
        return status;
    }

    public List<ProductRelease> getProductsRelease() {
        return productsRelease;
    }

    public ZonedDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public ZonedDateTime getRealizationDateTime() {
        return realizationDateTime;
    }
}
