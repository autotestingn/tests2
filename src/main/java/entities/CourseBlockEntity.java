package entities;

import java.time.LocalDate;

public class CourseBlockEntity {
    private String name;
    private LocalDate startDate;

    public CourseBlockEntity(String name, LocalDate startDate) {
        this.name = name;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "CourseBlockEntity{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
