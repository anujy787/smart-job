# SmartJob - A Dynamic Job Board Application

## 📌 Project Group Number: 9

## 🚀 Overview
SmartJob is an innovative job board application designed to simplify and enhance the job search and recruitment process. By offering a seamless and feature-rich platform, SmartJob connects job seekers with employers efficiently, ensuring an optimal hiring experience for both parties.

## 🎯 Problem Statement
Finding the right job or candidate in today's fast-paced job market is a complex and time-consuming process. Many job board applications lack personalized recommendations, efficient search mechanisms, and real-time notifications, leading to frustration among both job seekers and employers. Additionally, managing job postings, applications, and communication within a single platform often results in inefficiencies and poor user experience. Our solution, SmartJob, aims to bridge this gap by providing a feature-rich, scalable, and intelligent job board application. By incorporating modern software engineering principles, SmartJob ensures a seamless, user-friendly experience with dynamic job recommendations, real-time alerts, and secure interactions. This project will not only streamline the hiring process but also create an engaging and efficient platform for all users.

## 🏗️ Architecture & Design Patterns

SmartJob implements several design patterns to ensure maintainable, scalable, and extensible code:

### 1. Factory Pattern
- **JobPostFactory**: Creates different types of job posts (Full-time, Part-time, Remote, Internship)
- **UserFactory**: Creates different types of users (JobSeeker, Employer)
- **SearchStrategyFactory**: Creates search strategies based on search type for jobs
- **ApplicationSearchStrategyFactory**: Creates search strategies for job applications

### 2. Builder Pattern
- **JobPostBuilder**: Constructs job post objects with many optional parameters

### 3. Command Pattern
- **JobPostCommand**: Interface defining commands for job posts
- **UpdateJobPostCommand**: Executes job post update operation
- **DeletePostCommand**: Executes job post delete operation
- **JobPostInvoker**: Invokes the appropriate command

### 4. Strategy Pattern
- **SearchStrategy<T>**: Generic interface defining search behavior
- **TitleSearchStrategy**: Searches jobs by title
- **CompanySearchStrategy**: Searches jobs by company
- **LocationSearchStrategy**: Searches jobs by location
- **ApplicationTitleSearchStrategy**: Searches applications by job title
- **ApplicationCompanySearchStrategy**: Searches applications by company name
- **ApplicationLocationSearchStrategy**: Searches applications by location
- **SearchContext<T>**: Sets and executes the appropriate search strategy

### 5. Observer Pattern
- **NotificationObserver**: Interface for notification observers
- **JobSeekerNotificationObserver**: Concrete observer for job seekers
- **NotificationSubject**: Subject that notifies observers of events

### 6. State Pattern
- **ApplicationState**: Interface for job application states
- **AppliedState**: Represents the 'applied' state
- **InReviewState**: Represents the 'in review' state
- **HiredState**: Represents the 'hired' state
- **RejectedState**: Represents the 'rejected' state
- **WithdrawnState**: Represents the 'withdrawn' state

### 7. Adapter Pattern
- **JobApplicationEmailAdapter**: Adapts the EmailService for specific job application emails

### 8. Singleton Pattern
- Spring beans are singletons by default, including services and repositories

### 9. Dependency Injection
- Used throughout the application via Spring's @Autowired annotation

### 10. Repository Pattern
- All database access is abstracted through repositories (JobPostRepository, UserRepository, etc.)

## 📋 API Documentation

### Authentication
| Method | Endpoint | Description | Request Params/Body | Response |
|--------|----------|-------------|---------------------|----------|
| GET | `/login` | Show login page | None | Login page |
| POST | `/login` | Process login | `email`, `password` | Redirect to dashboard or error |
| GET | `/signup` | Show signup page | None | Signup page |
| POST | `/signup` | Process signup | `email`, `password`, `firstName`, `lastName`, `role` + role-specific fields | Forward to `/signup/process` |
| POST | `/signup/process` | Complete signup | Various role-specific fields | Redirect to login or error |
| GET | `/logout` | Log out user | None | Redirect to landing page |

### Job Posts
| Method | Endpoint | Description | Request Params/Body | Response |
|--------|----------|-------------|---------------------|----------|
| GET | `/jobs/create` | Show job creation form | None | Job creation page |
| POST | `/jobs/create` | Create new job | `JobPost` object | Redirect to dashboard |
| GET | `/jobs/{id}` | View job details | Path variable: `id` | Job details page |
| GET | `/jobs/edit/{id}` | Show job edit form | Path variable: `id` | Job edit page |
| POST | `/jobs/update/{id}` | Update job | Path variable: `id`, `JobPost` object | Redirect to dashboard |
| DELETE | `/jobs/delete/{id}` | Delete job | Path variable: `id` | Success/error response |
| GET | `/jobs/search` | Search jobs | Query params: `searchTerm`, `searchType` | Job search results |
| GET | `/browse-jobs` | Browse all jobs | None | Jobs listing page |

### Applications
| Method | Endpoint | Description | Request Params/Body | Response |
|--------|----------|-------------|---------------------|----------|
| POST | `/applications/apply` | Apply for job | `JobSeeker` object, `JobPost` object | JobApplication object |
| POST | `/applications/apply/{jobId}` | Apply from job details | Path variable: `jobId` | Redirect to applications page |
| POST | `/applications/withdraw/{id}` | Withdraw application | Path variable: `id` | Redirect to applications page |
| POST | `/applications/in-review/{id}` | Set application in review | Path variable: `id` | Redirect to manage-applications |
| POST | `/applications/hire/{id}` | Set application as hired | Path variable: `id` | Redirect to manage-applications |
| POST | `/applications/reject/{id}` | Set application as rejected | Path variable: `id` | Redirect to manage-applications |
| GET | `/applications/jobseeker/{id}` | Get applications by job seeker | Path variable: `id` | List of applications |
| GET | `/applications` | Show user applications | None | Applications page |
| GET | `/applications/search` | Search applications | Query params: `searchTerm`, `searchType` | Application search results |
| GET | `/manage-applications` | Show employer's managed applications | None | Manage applications page |

### Notifications
| Method | Endpoint | Description | Request Params/Body | Response |
|--------|----------|-------------|---------------------|----------|
| GET | `/notifications` | Get user notifications | None | Notifications page |
| POST | `/notifications/mark-read/{id}` | Mark notification as read | Path variable: `id` | Success response |
| POST | `/notifications/mark-all-read` | Mark all notifications as read | None | Success response |
| POST | `/notifications/delete-read` | Delete all read notifications | None | Success response |
| DELETE | `/notifications/delete/{id}` | Delete specific notification | Path variable: `id` | Success response |
| GET | `/notifications/unread-count` | Get unread notification count | None | Count of unread notifications |

### Pages
| Method | Endpoint | Description | Request Params/Body | Response |
|--------|----------|-------------|---------------------|----------|
| GET | `/` | Show landing page | None | Landing page |
| GET | `/dashboard` | Show dashboard | None | Dashboard page specific to user role |

## 🔄 Data Flow

1. **User Registration/Login**:
   - Users register as either JobSeekers or Employers
   - Authentication handled through AuthController
   - User data persisted via UserRepository

2. **Job Posting**:
   - Employers create job posts through JobPostController
   - JobPostFactory creates appropriate job type
   - JobPost data persisted via JobPostRepository
   - Notification sent to JobSeekers via Observer pattern

3. **Job Search**:
   - JobSeekers search jobs via Strategy pattern
   - SearchStrategy and SearchContext handle search logic
   - Results displayed via BrowseJobsController

4. **Job Application**:
   - JobSeekers apply for jobs via ApplicationController
   - Application status managed via State pattern
   - Status changes trigger notifications
   - Email notifications sent via JobApplicationEmailAdapter

5. **Application Management**:
   - Employers manage applications via EmployerApplicationsController
   - Status changes managed via Command pattern
   - Email notifications sent to applicants on status changes

6. **Notification System**:
   - Notifications created for various system events
   - Users can mark notifications as read, delete individual notifications
   - Batch operations for managing multiple notifications

## 🧩 Model Objects

### User Model
- Abstract base class for users
- Extended by JobSeeker and Employer
- Uses single table inheritance with discriminator column

### JobPost Model
- Represents job postings with various attributes
- Related to User (employer) through ManyToOne relationship

### JobApplication Model
- Represents a job application
- Contains state management for application status
- Linked to both JobSeeker and JobPost

### Notification Model
- Represents notifications sent to users
- Contains message, type, and read status

## 🎨 Frontend Implementation

SmartJob uses Thymeleaf as a templating engine to create a responsive, user-friendly interface. The frontend implementation includes:

### Key Features

1. **Responsive Design**
   - Bootstrap 5 framework for mobile-friendly layouts
   - Custom CSS with root variables for consistent theming
   - Optimized for various screen sizes

2. **Interactive UI Components**
   - Dynamic form handling with JavaScript validation
   - Real-time notification system with mark-as-read and delete functionality
   - AJAX-powered job and application search functionality
   - Modal confirmations for critical actions

3. **Role-Based Interface**
   - Customized dashboards for job seekers and employers
   - Conditional rendering based on user type
   - Context-aware navigation elements

### View Templates Structure

- **Layout**
  - `base.html`: Base template with common structure
  - Consistent styling with custom CSS variables

- **Authentication**
  - `login.html`: User login with validation
  - `signup.html`: User registration with dynamic form fields based on role selection

- **Job Management**
  - `browse-jobs.html`: Grid view of available job listings
  - `job-details.html`: Detailed view of a specific job post with application status
  - `post-job.html`: Form for creating new job listings
  - `edit-job.html`: Form for updating existing job listings

- **Application Management**
  - `applications.html`: Job seeker's view of their applications with search functionality
  - `manage-applications.html`: Employer's view for reviewing applicants

- **Notifications**
  - `notifications.html`: List view of user notifications with read/unread status
  - Delete functionality for individual notifications and batch operations
  - Real-time counter in the navigation bar

- **Dashboard**
  - Role-specific dashboards with quick actions
  - Search functionality for employers to find their job posts
  - Interactive data visualization

- **Email Templates**
  - `welcome_jobseeker.html`: Onboarding email for new job seekers
  - `welcome_employer.html`: Onboarding email for new employers
  - `applied_notification.html`: Confirmation email when a job seeker applies for a job
  - `hired_notification.html`: Notification when a job seeker is hired
  - `rejected_notification.html`: Notification when a job application is rejected
  - `withdrawn_notification.html`: Confirmation when a job application is withdrawn

### UI/UX Design Features

- **Color Scheme**
  - Primary color: #4a6bff (blue) - Used for primary actions and branding
  - Secondary color: #6c757d (gray) - Used for secondary elements
  - Success color: #28a745 (green) - Used for positive feedback
  - Background color: #f8f9fa (light gray) - Used for page backgrounds
  - Card shadow: 0 4px 6px rgba(0, 0, 0, 0.1) - Used for depth and elevation

- **Typography**
  - Modern font stack using 'Inter', 'Poppins', and fallback fonts
  - Text hierarchy with varying weights and sizes
  - Consistent spacing and alignment

- **Interactive Elements**
  - Hover effects on cards and buttons
  - Smooth transitions with CSS animations (fadeIn animation for cards)
  - Loading indicators for asynchronous operations

- **Accessibility Features**
  - Semantic HTML structure
  - High contrast text for readability
  - Font Awesome icons with appropriate text alternatives

## 🔧 Technologies Used

- **Backend**: Spring Boot, Java
- **Frontend**: HTML, CSS, JavaScript, Thymeleaf, Bootstrap 5
- **Data Access**: Spring Data JPA
- **Security**: Basic authentication and session management
- **Email**: Mailgun API integration

## 📊 Project Structure
```
smartjob/
├── src/
│   ├── main/
│   │   ├── java/edu/neu/csye7374/smartjob/
│   │   │   ├── commands/               # Command pattern implementation
│   │   │   ├── config/                 # Configuration classes
│   │   │   ├── controller/             # MVC controllers
│   │   │   ├── factory/                # Factory pattern implementations
│   │   │   ├── invokers/               # Command invokers
│   │   │   ├── model/                  # Domain model objects
│   │   │   ├── observer/               # Observer pattern implementation
│   │   │   ├── repository/             # Data access repositories
│   │   │   ├── service/                # Business logic services
│   │   │   │   └── state/              # State pattern implementation
│   │   │   ├── strategy/               # Strategy pattern implementation
│   │   │   ├── utils/                  # Utility classes
│   │   │   └── SmartjobApplication.java # Main application class
│   │   │
│   │   └── resources/
│   │       ├── static/                 # Static resources
│   │       │   ├── css/                # CSS stylesheets
│   │       │   └── js/                 # JavaScript files
│   │       │
│   │       └── templates/              # Thymeleaf templates
│   │           ├── layout/             # Layout templates
│   │           └── emails/             # Email templates
│   │
└── pom.xml                            # Maven configuration
```                             
## 🚀 Getting Started

### Prerequisites
- JDK 11+
- Maven
- MySQL Database

### Installation Steps
1. Clone the repository
2. Configure database settings in `application.properties`
3. Run `mvn clean install` to build the project
4. Start the application with `mvn spring-boot:run`
5. Access the application at `http://localhost:8080`

## 🆕 Recent Updates

### 1. Notification System Enhancements
- Added ability to delete individual notifications
- Added functionality to batch delete all read notifications
- Improved notification UI with better visual indicators and interaction

### 2. Application State and Email Notifications
- Implemented email notifications for application status changes
- Created email templates for different application states (Applied, Hired, Rejected, Withdrawn)
- Integrated the EmailService with JobApplicationEmailAdapter for better separation of concerns

### 3. Enhanced Search Functionality
- Added generic search capabilities with parameterized SearchStrategy interface
- Implemented application-specific search strategies for title, company, and location
- Created improved search UI for job applications with filtering options

### 4. Job Application UX Improvements
- Enhanced job details page to show application status for logged-in users
- Disabled apply button for jobs the user has already applied to
- Added status indicators on application listings

## 🤝 Contributing
### TEAM MEMBERS
- [@Anuj Yogesh Sharma](https://github.com/anujy787)
- [@Nilraj Mayekar](https://github.com/NilrajMayekar)
- [@Anirudh Maheshwari](https://github.com/anirudhm4)
- [@Rohith Varma Datla](https://github.com/Rohithvarma8)
- [@Bhuvan Dama Venkatesh Raj](https://github.com/bhuvan-dv)
Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License
This project is licensed under the MIT License - see the LICENSE.md file for details.