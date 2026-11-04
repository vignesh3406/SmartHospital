
package entity;

public abstract class Person {

    private int personId;
    private String firstName;
    private String lastName;
    private String phone;


    public Person(String firstName, String lastName, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
    }

    // Getters
    public int getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }


    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("First name cannot be empty");
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }

    public void setPhone(String phone) {
        if (phone != null && phone.matches("\\d{10}")) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Phone must contain exactly 10 digits");
        }
    }

    // Business methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean validateContactInfo() {
        return phone != null && phone.matches("\\d{10}");
    }
}
