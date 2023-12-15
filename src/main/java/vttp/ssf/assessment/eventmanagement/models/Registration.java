package vttp.ssf.assessment.eventmanagement.models;

import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public class Registration {

    @NotBlank(message = "This field is mandatory!")
    @Length(min = 5, max = 25, message = "Your name cannot be shorter than 5 characters and longer than 25 characters!")
    private String fullName;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birth Date must be before today!")
    private LocalDate birthDate;
    
    @Email(message = "Please enter a valid email!")
    @Length(max = 50, message = "Length of email cannot exceed 50 characters!")
    @NotBlank(message = "This field is mandatory!")
    private String email;

    @Pattern(regexp = "(8|9)[0-9]{7}", message = "Phone number must start with 8 or 9 and have 8 digits!")
    private String phoneNo;
    
    @Min(value = 1, message = "Minimum number of tickets is 1!")
    @Max(value = 3, message = "Maximum number of tickets is 3!")
    private Integer ticketsRequested;

    private String gender;
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getTicketsRequested() {
        return ticketsRequested;
    }

    public void setTicketsRequested(Integer ticketsRequested) {
        this.ticketsRequested = ticketsRequested;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    
}
