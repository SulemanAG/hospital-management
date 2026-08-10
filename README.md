

# 🏥 Hospital Management System

> A robust, role-based Java application engineered with Object-Oriented Programming (OOP) principles, custom exception handling, interface-driven workflows, and automated object serialization persistence.

[![Java Version](https://img.shields.io/badge/Java-17%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📌 Overview

The **Hospital Management System** models a full-scale healthcare ecosystem. It provides an interactive workspace for **Admins, Doctors, Nurses, Pharmacists, and Patients**.

The system automates patient check-in workflows, emergency triage routing, preliminary nurse vitals reports, doctor diagnoses and treatment plans, pharmacy prescription fulfillment, and hospital financial accounting (including bill collections and monthly staff payroll disbursements).

---

## 🏛️ System Architecture & Class Hierarchy

```mermaid
classDiagram
    class Serializable {
        <<interface>>
    }
    class Payable {
        <<interface>>
        +getBalance() double
        +processPayment(amount) void
        +getPaymentStatus() String
    }
    class MedicalPreparer {
        <<interface>>
        +prepareCheckupReport(patient) MedicalReport
    }
    class Diagnosable {
        <<interface>>
        +diagnosePatient(patient, report) void
    }

    class Person {
        <<abstract>>
        #String id
        #String name
        #String phoneNumber
        #int age
        #String gender
        +getRole()* String
        +displayDetails()* void
    }

    class Employee {
        <<abstract>>
        #String employeeId
        #double baseSalary
        #String department
        #double accruedSalaryBalance
    }

    class Doctor {
        -String specialization
        -String doctorLevel
        -boolean isAvailable
    }

    class Nurse {
        -String assignedWard
        -String shiftTime
    }

    class Pharmacist {
        -String licenseNumber
    }

    class Patient {
        -String medicalHistory
        -String status
        -boolean isEmergency
        -double billBalance
    }

    class DataManager {
        +loadData() HospitalDataWrapper
        +saveData(data) void
    }

    Serializable <|-- Person
    Person <|-- Employee
    Person <|-- Patient
    Employee <|-- Doctor
    Employee <|-- Nurse
    Employee <|-- Pharmacist

    Payable <|.. Patient
    Payable <|.. Employee
    MedicalPreparer <|.. Nurse
    Diagnosable <|.. Doctor
    DataManager ..> Person : Persists

```

---

## 🔄 End-to-End Patient Flow & Lifecycle Architecture

### 1. High-Level Lifecycle Flowchart

```mermaid
flowchart TD
    A[🚑 Patient Arrives at Hospital] --> B{Is Emergency?}
    
    %% Emergency Path
    B -- YES --> C[🚨 Triage Alert: Bypass Queue]
    C --> D[Assign Senior Doctor & ICU Nurse]
    D --> E[Immediate ICU Stabilization]
    
    %% Standard Path
    B -- NO --> F[📝 Reception Check-In]
    F --> G[Assign Low-Level/Junior Doctor & Ward Nurse]
    G --> H[Nurse Prepares Vitals & MedicalReport]
    H --> I[Doctor Reviews Vitals & Diagnoses]
    
    %% Treatment Decision
    E --> J{Requires Admission?}
    I --> J
    
    J -- YES --> K[🛌 Ward Admission & Continued Care]
    K --> L[Treatment Completed -> Discharge Issued]
    J -- NO --> M[💊 Prescription Issued]
    
    L --> N[🏥 Pharmacy Fulfillment]
    M --> N
    
    N --> O[💳 Central Billing & Financial Payment]
    O --> P[💰 Funds Credited to Hospital Treasury]
    P --> Q[💸 Monthly Staff Payroll Disbursement]

```

---

### 2. Detailed Patient Journey Sequence

```mermaid
sequenceDiagram
    autonumber
    actor P as Patient
    participant N as Nurse
    participant D as Doctor
    participant Ph as Pharmacist
    participant F as Hospital Treasury

    Note over P, F: Phase 1: Intake & Preliminary Vitals
    P->>N: Arrives at Hospital (Check-in)
    N->>N: Measure Vitals (BP, Pulse, Temp, Symptoms)
    N->>D: Route generated MedicalReport

    Note over P, F: Phase 2: Consultation & Treatment Plan
    D->>P: Perform Clinical Examination
    D->>P: Issue Diagnosis & Prescription
    alt Admission Required
        D->>P: Set status to ADMITTED (Ward/ICU)
        P->>P: Undergo Recovery Treatment
        D->>P: Set status to DISCHARGED
    end

    Note over P, F: Phase 3: Pharmacy & Financial Settlement
    P->>Ph: Present Prescription at Pharmacy
    Ph->>P: Verify Stock, Dispense Medicines & Issue Bill
    P->>F: Settle Total Hospital & Pharmacy Charges
    F->>F: Deposit revenue into Central Treasury Account

    Note over P, F: Phase 4: Administrative Payroll
    F->>N: Disburse Monthly Salary
    F->>D: Disburse Monthly Salary

```

---

## ⚡ Role-Based Dashboard Capabilities

### 🔑 Role Breakdown

* **👨‍💼 Admin Dashboard:**
* Register staff members (`Doctor`, `Nurse`, `Pharmacist`) with real-time validation.
* Create and assign clinical departments.
* Audit hospital central treasury funds.
* Disburse monthly staff salaries (`FinanceService`).


* **👨‍⚕️ Doctor Dashboard:**
* Review preliminary medical reports generated by nurses.
* Prescribe medications and outline recovery treatments.
* Manage patient bed admissions and discharge authorizations.


* **👩‍⚕️ Nurse Dashboard:**
* Check in arriving patients and assess urgency.
* Measure vital signs (BP, heart rate, temperature) and log symptoms.
* Compile preliminary `MedicalReport` instances for assigned doctors.


* **💊 Pharmacist Dashboard:**
* Manage inventory quantities and prices for `Medicine`.
* Verify doctor prescriptions on medical reports.
* Dispense medications, collect bill payments, and issue receipts.


* **🤒 Patient Dashboard:**
* Check admission/recovery status and view assigned medical team.
* Read detailed medical reports and active prescriptions.
* Pay outstanding hospital and pharmacy bills (`Payable`).



---

## 📁 Project Structure

```text
src/
 ├── hospital/
 │    ├── model/                      # Data Entities & Class Hierarchies
 │    │    ├── Person.java            # Abstract Parent Class
 │    │    ├── Employee.java          # Abstract Class for Staff Entities
 │    │    ├── Doctor.java            # Doctor Entity (Level & Specialization)
 │    │    ├── Nurse.java             # Nurse Entity (Ward & Shifts)
 │    │    ├── Pharmacist.java        # Pharmacist Entity (Licensing)
 │    │    ├── Patient.java           # Patient Entity (Vitals & Status)
 │    │    ├── Department.java        # Department Management Entity
 │    │    ├── MedicalReport.java     # Patient Checkup & Prescription Report
 │    │    └── Medicine.java          # Pharmacy Inventory Item
 │    │
 │    ├── interfaces/                 # Role Behavior Contracts
 │    │    ├── Payable.java           # Bills (Patients) & Salaries (Employees)
 │    │    ├── MedicalPreparer.java   # Nurse Preliminary Duties
 │    │    └── Diagnosable.java        # Doctor Diagnostic Duties
 │    │
 │    ├── exception/                  # Custom Exception Hierarchy
 │    │    ├── InvalidInputException.java
 │    │    ├── InsufficientStockException.java
 │    │    ├── InsufficientFundsException.java
 │    │    └── UserNotFoundException.java
 │    │
 │    ├── service/                    # Business & Operations Logic
 │    │    ├── HospitalService.java   # Main Operational Engine
 │    │    ├── PharmacyService.java   # Inventory & Dispensing Engine
 │    │    └── FinanceService.java    # Central Treasury & Payroll Engine
 │    │
 │    ├── util/                       # Utilities & Data Persistence
 │    │    └── DataManager.java       # File Serialization Engine (Save/Load)
 │    │
 │    └── ui/                         # Command Line Interfaces
 │         ├── AdminMenu.java
 │         ├── DoctorMenu.java
 │         ├── NurseMenu.java
 │         ├── PharmacistMenu.java
 │         └── PatientMenu.java
 │
 └── Main.java                        # Application Entry Point

```

---

## 🛠️ Getting Started

### Prerequisites

* **Java Development Kit (JDK):** Version 17 or higher installed.
* **IDE / Terminal:** IntelliJ IDEA, Eclipse, VS Code, or any terminal supporting `javac`.

### Installation & Run Instructions

1. **Clone the Repository:**
```bash
git clone [https://github.com/SulemanAG/hospital-management.git](https://github.com/SulemanAG/hospital-management.git)
cd hospital-management

```


2. **Compile the Source Code:**
```bash
javac -d bin src/hospital/*/*.java src/Main.java

```


3. **Run the Application:**
```bash
java -cp bin Main

```



---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to open an issue or submit a pull request.

---

