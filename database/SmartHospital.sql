-- SmartHospital Database - Complete SQL Script
-- MySQL 8.0+
-- Includes database creation, tables, sample data, and verification queries.

CREATE DATABASE IF NOT EXISTS SmartHospital;
USE SmartHospital;

-- -----------------------------------------------------
-- 1. TABLE CREATION
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS Clinic (
    ClinicID INT PRIMARY KEY AUTO_INCREMENT,
    ClinicName VARCHAR(100),
    City VARCHAR(50),
    ContactNumber VARCHAR(20)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Patient (
    PatientID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Gender VARCHAR(10),
    Phone VARCHAR(20),
    Email VARCHAR(100) UNIQUE,
    BloodGroup VARCHAR(5),
    RegisteredDate DATE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Doctor (
    DoctorID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Specialization VARCHAR(80),
    ClinicID INT,
    ConsultationFee DECIMAL(10,2),
    YearsOfExperience INT,
    CONSTRAINT FK_Doctor_Clinic FOREIGN KEY (ClinicID)
        REFERENCES Clinic(ClinicID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Appointment (
    AppointmentID INT PRIMARY KEY AUTO_INCREMENT,
    PatientID INT,
    DoctorID INT,
    AppointmentDate DATETIME NOT NULL,
    Status VARCHAR(20) DEFAULT 'Scheduled',
    Reason VARCHAR(200),
    CONSTRAINT FK_Appointment_Patient FOREIGN KEY (PatientID)
        REFERENCES Patient(PatientID),
    CONSTRAINT FK_Appointment_Doctor FOREIGN KEY (DoctorID)
        REFERENCES Doctor(DoctorID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Prescription (
    PrescriptionID INT PRIMARY KEY AUTO_INCREMENT,
    AppointmentID INT,
    MedicineName VARCHAR(100),
    Dosage VARCHAR(50),
    DurationDays INT,
    CONSTRAINT FK_Prescription_Appointment FOREIGN KEY (AppointmentID)
        REFERENCES Appointment(AppointmentID)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Billing (
    BillID INT PRIMARY KEY AUTO_INCREMENT,
    AppointmentID INT,
    Amount DECIMAL(10,2),
    PaymentStatus VARCHAR(20),
    BillDate DATE,
    CONSTRAINT FK_Billing_Appointment FOREIGN KEY (AppointmentID)
        REFERENCES Appointment(AppointmentID)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- 2. OPTIONAL RESET: UNCOMMENT ONLY IF YOU WANT TO DELETE
--    ALL EXISTING ROWS BEFORE REINSERTING THE SAMPLE DATA.
--    This keeps the tables and foreign keys.
-- -----------------------------------------------------
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE Billing;
-- TRUNCATE TABLE Prescription;
-- TRUNCATE TABLE Appointment;
-- TRUNCATE TABLE Doctor;
-- TRUNCATE TABLE Patient;
-- TRUNCATE TABLE Clinic;
-- SET FOREIGN_KEY_CHECKS = 1;

-- -----------------------------------------------------
-- 3. SAMPLE DATA
-- Run this section once on empty tables.
-- -----------------------------------------------------

INSERT INTO Clinic (ClinicName, City, ContactNumber) VALUES
('Meridian Main Clinic', 'Chennai', '9876543210'),
('Meridian Heart Clinic', 'Coimbatore', '9876543211'),
('Meridian Child Care', 'Madurai', '9876543212'),
('Meridian Health Centre', 'Trichy', '9876543213'),
('Meridian Regional Hospital', 'Salem', '9876543214');

INSERT INTO Patient
(FirstName, LastName, DateOfBirth, Gender, Phone, Email, BloodGroup, RegisteredDate)
VALUES
('Arun', 'Kumar', '2000-05-15', 'Male', '9876500001', 'arun@gmail.com', 'O+', '2026-10-01'),
('Priya', 'Sharma', '1998-08-20', 'Female', '9876500002', 'priya@gmail.com', 'A+', '2026-10-02'),
('Rahul', 'Raj', '1995-03-10', 'Male', '9876500003', 'rahul@gmail.com', 'B+', '2026-10-03'),
('Divya', 'Devi', '2002-11-25', 'Female', '9876500004', 'divya@gmail.com', 'AB+', '2026-10-04'),
('Karthik', 'S', '1989-07-12', 'Male', '9876500005', 'karthik@gmail.com', 'O-', '2026-10-05');

INSERT INTO Doctor
(FirstName, LastName, Specialization, ClinicID, ConsultationFee, YearsOfExperience)
VALUES
('Ramesh', 'K', 'Cardiologist', 1, 800.00, 12),
('Meena', 'R', 'Dermatologist', 2, 600.00, 8),
('Suresh', 'M', 'Pediatrician', 3, 500.00, 10),
('Anitha', 'S', 'Neurologist', 4, 1000.00, 15),
('Vijay', 'P', 'General Physician', 5, 400.00, 5);

INSERT INTO Appointment
(PatientID, DoctorID, AppointmentDate, Status, Reason)
VALUES
(1, 1, '2026-11-02 10:00:00', 'Scheduled', 'Chest pain'),
(2, 2, '2026-11-02 11:00:00', 'Scheduled', 'Skin allergy'),
(3, 3, '2026-11-03 09:30:00', 'Scheduled', 'Regular checkup'),
(4, 4, '2026-11-03 12:00:00', 'Scheduled', 'Headache'),
(5, 5, '2026-11-04 10:30:00', 'Scheduled', 'Fever');

INSERT INTO Prescription
(AppointmentID, MedicineName, Dosage, DurationDays)
VALUES
(1, 'Aspirin', '75mg once daily', 5),
(1, 'Atorvastatin', '10mg once daily', 10),
(2, 'Cetirizine', '10mg once daily', 5),
(3, 'Paracetamol', '500mg twice daily', 3),
(4, 'Ibuprofen', '200mg twice daily', 5),
(5, 'Paracetamol', '500mg twice daily', 3);

INSERT INTO Billing
(AppointmentID, Amount, PaymentStatus, BillDate)
VALUES
(1, 800.00, 'Paid', '2026-11-02'),
(2, 600.00, 'Pending', '2026-11-02'),
(3, 500.00, 'Paid', '2026-11-03'),
(4, 1000.00, 'Pending', '2026-11-03'),
(5, 400.00, 'Paid', '2026-11-04');

-- -----------------------------------------------------
-- 4. BASIC VERIFICATION
-- -----------------------------------------------------
SHOW TABLES;

SELECT 'Clinic' AS TableName, COUNT(*) AS Total FROM Clinic
UNION ALL SELECT 'Patient', COUNT(*) FROM Patient
UNION ALL SELECT 'Doctor', COUNT(*) FROM Doctor
UNION ALL SELECT 'Appointment', COUNT(*) FROM Appointment
UNION ALL SELECT 'Prescription', COUNT(*) FROM Prescription
UNION ALL SELECT 'Billing', COUNT(*) FROM Billing;

-- -----------------------------------------------------
-- 5. JOIN / REPORT QUERIES
-- -----------------------------------------------------

-- All patients
SELECT * FROM Patient;

-- Doctors with their clinics
SELECT d.DoctorID, d.FirstName, d.LastName, d.Specialization,
       c.ClinicName, d.ConsultationFee
FROM Doctor d
JOIN Clinic c ON d.ClinicID = c.ClinicID;

-- Appointments with patient and doctor details
SELECT a.AppointmentID,
       CONCAT(p.FirstName, ' ', p.LastName) AS PatientName,
       CONCAT(d.FirstName, ' ', d.LastName) AS DoctorName,
       a.AppointmentDate, a.Status, a.Reason
FROM Appointment a
JOIN Patient p ON a.PatientID = p.PatientID
JOIN Doctor d ON a.DoctorID = d.DoctorID;

-- Prescription details
SELECT pr.PrescriptionID, a.AppointmentID, p.FirstName AS PatientName,
       pr.MedicineName, pr.Dosage, pr.DurationDays
FROM Prescription pr
JOIN Appointment a ON pr.AppointmentID = a.AppointmentID
JOIN Patient p ON a.PatientID = p.PatientID;

-- Billing details
SELECT b.BillID, a.AppointmentID,
       CONCAT(p.FirstName, ' ', p.LastName) AS PatientName,
       b.Amount, b.PaymentStatus, b.BillDate
FROM Billing b
JOIN Appointment a ON b.AppointmentID = a.AppointmentID
JOIN Patient p ON a.PatientID = p.PatientID;

-- Revenue summary
SELECT SUM(Amount) AS TotalBilled,
       SUM(CASE WHEN PaymentStatus = 'Paid' THEN Amount ELSE 0 END) AS PaidAmount,
       SUM(CASE WHEN PaymentStatus = 'Pending' THEN Amount ELSE 0 END) AS PendingAmount
FROM Billing;

-- Foreign key verification
SELECT TABLE_NAME, COLUMN_NAME, CONSTRAINT_NAME,
       REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = DATABASE()
  AND REFERENCED_TABLE_NAME IS NOT NULL
ORDER BY TABLE_NAME, COLUMN_NAME;
