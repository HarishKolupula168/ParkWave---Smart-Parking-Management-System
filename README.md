# ParkWave - Multi-Mall Parking Booking System

A comprehensive parking management system for shopping malls with real-time slot booking and interactive maps.

## Features
- Multi-mall parking system with 5 major shopping malls
- Interactive parking maps with real-time slot availability
- Guest booking functionality (no registration required)
- Multi-level parking structures with zone organization
- Priority-based parking zones (Near Entrance, Middle, Far)
- Complete booking lifecycle management
- Responsive web interface

## Tech Stack
- Backend: Spring Boot, Spring Data JPA, Hibernate
- Frontend: HTML, CSS, JavaScript
- Database: MySQL
- Build Tool: Maven

## APIs
- Mall Management: `/api/malls`, `/api/malls/random`
- Parking Slots: `/api/mall-parking/slots/{mallId}/level/{level}`
- Bookings: `/api/mall-bookings`, `/api/mall-bookings/guest`

## Getting Started
1. Clone the repository
2. Configure MySQL database
3. Run `mvn spring-boot:run`
4. Access at http://localhost:8080

## Project Structure
- `src/main/java/com/parkwave/` - Backend code
- `src/main/resources/static/` - Frontend pages
- `src/main/resources/application.properties` - Configuration

## Contributing
Pull requests are welcome!

## License
MIT License
