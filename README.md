# booking-hotel-api
# Hotel API for DS test

#Features
- **API running Endpoint:** [http://localhost:PORT/v1](http://localhost:PORT/v1)

#Additional libraries
- Swagger
- Spring HATEOAS
- LOG4J2
- JPA
- OpenAPI
- H2
- Lombok
- actuator

#Main Controller and Methods
- Reservation Controller (getReservations, searchAvailabilities, findById, update, create)
- Customer Controller
- Room Controller

#Simplest Post Booking Request  
{  
&nbsp;&nbsp;"customerBilledObj": {  
&nbsp;&nbsp;&nbsp;&nbsp;"customerId": existingCustomerId  
&nbsp;&nbsp;},  
&nbsp;&nbsp;"roomObj": {  
&nbsp;&nbsp;&nbsp;&nbsp;"roomId": existingRoomId  
&nbsp;&nbsp;},  
&nbsp;&nbsp;"startDate": availableStartDate,  
&nbsp;&nbsp;"endDate": availableEndDate  
}  

