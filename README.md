### Задание
<img width="1372" alt="Снимок экрана 2025-01-10 в 01 45 45" src="https://github.com/user-attachments/assets/2be930d2-3de7-40a6-a21f-4c576e5f1b74" />


```mermaid
classDiagram
direction BT
class AdminProcessingController {
  + AdminProcessingController(AdminProcessingService) 
  + getAllPotentialAdmin(int, int) ResponseEntity~Response~Page~PotentialAdmin~~~
  + approveAdminRole(Long, PotentialAdmin) ResponseEntity~Response~User~~
  + rejectAdminRole(PotentialAdmin) ResponseEntity~Response~User~~
}
class AdminProcessingService {
<<Interface>>
  + addUserToWaitingList(User) void
  + getPotentialAdmins(Pageable) Page~PotentialAdmin~
  + rejectUserForAdminRole(User) void
  + giveAdminRoleToUser(Long, User) void
  + removeUserFromWaitingList(User) void
   boolean anyAdminExist
   List~PotentialAdmin~ allPotentialAdmins
}
class AdminProcessingServiceImpl {
  + AdminProcessingServiceImpl(UserRepository, PotentialAdminRepository) 
  + getPotentialAdmins(Pageable) Page~PotentialAdmin~
  + rejectUserForAdminRole(User) void
  + giveAdminRoleToUser(Long, User) void
  + removeUserFromWaitingList(User) void
  + addUserToWaitingList(User) void
   boolean anyAdminExist
   List~PotentialAdmin~ allPotentialAdmins
}
class AsyncConfiguration {
  + AsyncConfiguration() 
   AsyncUncaughtExceptionHandler? asyncUncaughtExceptionHandler
   Executor? asyncExecutor
}
class AsyncServiceUserProcessing {
<<Interface>>
  + runAsync(List~StudyGroupDto~, User) void
}
class AuthExceptionHandler {
  + AuthExceptionHandler() 
  + jwtTokenExpired(JwtTokenExpired) Response~String~
  + userNotFound(UserNotFoundException) Response~String~
  + userConflict(UserConflictException) Response~String~
}
class AuthService {
<<Interface>>
  + signIn(UserDto) JwtAuthenticationResponse
  + signUp(UserDto) JwtAuthenticationResponse
   UserRole userRole
   String username
   User currentUser
}
class AuthServiceImpl {
  + AuthServiceImpl(UserService, JwtService, PasswordEncoder, AuthenticationManager, AdminProcessingService) 
  - setAdminOrAddToWaitList(User) User
  + signIn(UserDto) JwtAuthenticationResponse
  + signUp(UserDto) JwtAuthenticationResponse
   UserRole userRole
   String username
   User currentUser
}
class AuthenticationUtils {
  + AuthenticationUtils() 
  + verifyAccess(CreatorAware) void
   User currentUserFromContext
}
class AuthorizationController {
  + AuthorizationController(AuthService) 
  + registration(UserDto) ResponseEntity~Response~JwtAuthenticationResponse~~
  + helloWorld() ResponseEntity~String~
  + isTokenValid() Response~String~
  + login(UserDto) Response~JwtAuthenticationResponse~
  + helloPrivate() ResponseEntity~String~
   Response~UserRole~ userRole
   ResponseEntity~Response~String~~ username
}
class CSVParser {
  + CSVParser() 
  - parsePerson(CSVRecord) PersonDto
  - parseStudyGroup(CSVRecord) StudyGroupDto
  - getRecordValue(CSVRecord, int) String
  + getStudyGroupsFromFile(File) ArrayList~StudyGroupDto~
  - parseCoordinates(CSVRecord) Coordinates
  - parseLocation(CSVRecord) Location
}
class Color {
<<enumeration>>
  + Color() 
  + values() Color[]
  + valueOf(String) Color
}
class CoordinateService {
<<Interface>>
  + addAll(List~Coordinates~) List~Coordinates~
}
class Coordinates {
  + Coordinates() 
  - Long id
  - Float x
  - Float y
  + equals(Object) boolean
  # canEqual(Object) boolean
  + hashCode() int
  + toString() String
   Long id
   Float x
   Float y
}
class CoordinatesRepository {
<<Interface>>

}
class CoordinatesServiceImpl {
  + CoordinatesServiceImpl(CoordinatesRepository) 
  + addAll(List~Coordinates~) List~Coordinates~
}
class Country {
<<enumeration>>
  + Country() 
  + values() Country[]
  + valueOf(String) Country
}
class CreatorAware {
<<Interface>>
   User creator
}
class EclipseLinkJpaConfiguration {
  # EclipseLinkJpaConfiguration(DataSource, JpaProperties, ObjectProvider~JtaTransactionManager~) 
  + transactionManager(EntityManagerFactory) PlatformTransactionManager
  + dataSource() DataSource
  + properties() JpaProperties
  # createJpaVendorAdapter() AbstractJpaVendorAdapter
  + localContainerEntityManagerFactory(EntityManagerFactoryBuilder, DataSource) LocalContainerEntityManagerFactoryBean
  - initJpaProperties() Map~String, ?~
   Map~String, Object~ vendorProperties
}
class FailedToReadFile {
  + FailedToReadFile(String) 
}
class FieldsHelper {
<<enumeration>>
  - FieldsHelper(int) 
  - int index
  + values() FieldsHelper[]
  + valueOf(String) FieldsHelper
   int index
}
class FistLabApplication {
  + FistLabApplication() 
  + main(String[]) void
}
class FormOfEducation {
<<enumeration>>
  + FormOfEducation() 
  + values() FormOfEducation[]
  + valueOf(String) FormOfEducation
}
class GlobalHandler {
  + GlobalHandler() 
  + methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException) Response~String~
}
class ImportAsyncUncaughtExceptionHandler {
  + ImportAsyncUncaughtExceptionHandler() 
  + handleUncaughtException(Throwable, Method, Object[]) void
}
class ImportController {
  + ImportController(ImportService, AuthService) 
  + dropAll() void
  + importStudyGroups(MultipartFile, Timestamp) ResponseEntity~Response~Integer~~
}
class ImportMode {
<<enumeration>>
  + ImportMode() 
  + values() ImportMode[]
  + valueOf(String) ImportMode
}
class ImportProcessing {
<<Interface>>

}
class ImportProcessingImpl {
  + ImportProcessingImpl(SequentialQueueProcessor, PersonService, StudyGroupService, EntityManager, LocationService, CoordinateService, OperationService) 
  + runImport(List~StudyGroupDto~, User, ImportMode) void
  + runAsync(List~StudyGroupDto~, User) void
  + runSeq(List~StudyGroupDto~, User) void
}
class ImportService {
<<Interface>>
  + importFile(MultipartFile, User, Timestamp) String
  + dropAll() void
}
class ImportServiceImpl {
  + ImportServiceImpl(StudyGroupRepository, PersonRepository, ImportProcessing, LocationRepository) 
  - getFile(MultipartFile) File
  + dropAll() void
  + importFile(MultipartFile, User, Timestamp) String
}
class InvalidActionException {
  + InvalidActionException(String) 
}
class InvalidFieldException {
  + InvalidFieldException(String) 
}
class JwtAuthenticationFilter {
  + JwtAuthenticationFilter(JwtService, UserService) 
  # doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) void
}
class JwtAuthenticationResponse {
  + JwtAuthenticationResponse(String) 
  - String token
  + equals(Object) boolean
  # canEqual(Object) boolean
  + hashCode() int
  + toString() String
   String token
}
class JwtService {
<<Interface>>
  + extractUserName(String) String
  + isTokenValid(String, UserDetails) boolean
  + generateToken(UserDetails) String
}
class JwtServiceImpl {
  + JwtServiceImpl() 
  - extractClaim(String, Function~Claims, T~) T
  + extractUserName(String) String
  - generateToken(Map~String, Object~, UserDetails) String
  + isTokenValid(String, UserDetails) boolean
  - extractExpiration(String) Date
  - isTokenExpired(String) boolean
  - extractAllClaims(String) Claims
  + generateToken(UserDetails) String
   SecretKey signingKey
}
class JwtTokenExpired {
  + JwtTokenExpired(String) 
}
class Location {
  + Location() 
  - Long id
  - Long x
  - float z
  - String name
  - double y
  # canEqual(Object) boolean
  + toString() String
  + equals(Object) boolean
  + hashCode() int
   Long x
   String name
   double y
   float z
   Long id
}
class LocationRepository {
<<Interface>>

}
class LocationService {
<<Interface>>
  + add(Location) Location
  + addAll(List~Location~) List~Location~
}
class LocationServiceImpl {
  + LocationServiceImpl(LocationRepository) 
  + add(Location) Location
  + addAll(List~Location~) List~Location~
}
class MappersExceptionHandler {
  + MappersExceptionHandler() 
  + fieldInvalid(InvalidFieldException) Response~String~
}
class NotEnoughRights {
  + NotEnoughRights(String) 
}
class Operation {
  + Operation() 
  - Long id
  - User user
  - Boolean isFinished
  - int amountOfObjectSaved
  - ImportMode mode
  + toString() String
  # canEqual(Object) boolean
  + hashCode() int
  + equals(Object) boolean
   int amountOfObjectSaved
   ImportMode mode
   Long id
   Boolean isFinished
   User user
}
class OperationRepository {
<<Interface>>

}
class OperationService {
<<Interface>>
  + addAll(List~Operation~) List~Operation~
  + add(Operation) Operation
}
class OperationServiceImpl {
  + OperationServiceImpl(OperationRepository) 
  + addAll(List~Operation~) List~Operation~
  + add(Operation) Operation
}
class Person {
  + Person(String, String, Color, Color, Location, int, long, Country, User) 
  + Person() 
  - String passportID
  - long weight
  - int height
  - User creator
  - Country nationality
  - Location location
  - String name
  - Color eyeColor
  - Color hairColor
  + hashCode() int
  + builder() PersonBuilder
  # canEqual(Object) boolean
  + toString() String
  + equals(Object) boolean
   String name
   long weight
   Country nationality
   String passportID
   User creator
   int height
   Color eyeColor
   Location location
   Color hairColor
}
class PersonBuilder {
  ~ PersonBuilder() 
  + eyeColor(Color) PersonBuilder
  + passportID(String) PersonBuilder
  + toString() String
  + name(String) PersonBuilder
  + hairColor(Color) PersonBuilder
  + location(Location) PersonBuilder
  + height(int) PersonBuilder
  + weight(long) PersonBuilder
  + nationality(Country) PersonBuilder
  + creator(User) PersonBuilder
  + build() Person
}
class PersonController {
  + PersonController(PersonService) 
  + getAllPersonsName(int, int) ResponseEntity~Response~Page~Person~~~
  + updatePerson(PersonDto) ResponseEntity~Response~Person~~
  + createPerson(PersonDto) ResponseEntity~Response~Person~~
  + deletePersonById(String) ResponseEntity~Response~String~~
}
class PersonDto {
  + PersonDto() 
  + PersonDto(Long, String, String, String, Location, int, long, String, String, UserDto) 
  - UserDto creator
  - String eyeColor
  - String passportID
  - Location location
  - int height
  - long weight
  - String nationality
  - Long id
  - String hairColor
  - String name
  + builder() PersonDtoBuilder
  + toString() String
  + hashCode() int
  + equals(Object) boolean
  # canEqual(Object) boolean
   String name
   long weight
   String hairColor
   Long id
   String passportID
   int height
   UserDto creator
   String nationality
   String eyeColor
   Location location
}
class PersonDtoBuilder {
  ~ PersonDtoBuilder() 
  + id(Long) PersonDtoBuilder
  + name(String) PersonDtoBuilder
  + eyeColor(String) PersonDtoBuilder
  + hairColor(String) PersonDtoBuilder
  + toString() String
  + location(Location) PersonDtoBuilder
  + height(int) PersonDtoBuilder
  + weight(long) PersonDtoBuilder
  + nationality(String) PersonDtoBuilder
  + build() PersonDto
  + passportID(String) PersonDtoBuilder
  + creator(UserDto) PersonDtoBuilder
}
class PersonExceptionHandler {
  + PersonExceptionHandler() 
  + personNotUnique(PersonNotUnique) Response~String~
  + userAlreadyExist(UserAlreadyExist) Response~String~
  + personNotExist(PersonNotExistException) Response~String~
  + personNotExist(InvalidActionException) Response~String~
  + notEnoughRights(NotEnoughRights) Response~String~
}
class PersonMapper {
  + PersonMapper() 
  + toEntity(PersonDto) Person
}
class PersonNotExistException {
  + PersonNotExistException(String) 
}
class PersonNotUnique {
  + PersonNotUnique(String) 
}
class PersonRepository {
<<Interface>>
  + findAll(Pageable) Page~Person~
  + findPersonByPassportID(String) Optional~Person~
  + findByPassportID(String) Optional~Person~
}
class PersonService {
<<Interface>>
  + add(Person) Person
  + addAll(List~Person~) List~Person~
  + deletePersonByPassportId(String) void
  + isExist(Person) boolean
  + updatePerson(Person) Person
  + getAllPersons(Pageable) Page~Person~
}
class PersonServiceImpl {
  + PersonServiceImpl(PersonRepository, AuthenticationUtils, StudyGroupRepository, LocationRepository) 
  + add(Person) Person
  + getAllPersons(Pageable) Page~Person~
  + deletePersonByPassportId(String) void
  + updatePerson(Person) Person
  + addAll(List~Person~) List~Person~
  + isExist(Person) boolean
}
class PotentialAdmin {
  + PotentialAdmin() 
  - Long id
  - User user
  - UserRole wantedRole
  + equals(Object) boolean
  # canEqual(Object) boolean
  + hashCode() int
  + toString() String
   Long id
   User user
   UserRole wantedRole
}
class PotentialAdminRepository {
<<Interface>>
  + removeByUser(User) void
  + findAll(Pageable) Page~PotentialAdmin~
}
class RejectedExecutionHandlerImpl {
  + RejectedExecutionHandlerImpl() 
  + rejectedExecution(Runnable, ThreadPoolExecutor) void
}
class Response~T~ {
  + Response(String, T) 
  + Response(T) 
  + Response(String) 
  - String message
  - Object body
   String message
   Object body
}
class SecurityConfig {
  + SecurityConfig(JwtAuthenticationFilter, UserService) 
  + passwordEncoder() PasswordEncoder
  + authenticationManager(AuthenticationConfiguration) AuthenticationManager
  + securityFilterChain(HttpSecurity) SecurityFilterChain
  + authenticationProvider() AuthenticationProvider
}
class Semester {
<<enumeration>>
  + Semester() 
  + values() Semester[]
  + valueOf(String) Semester
}
class SeqServiceUserProcessing {
<<Interface>>
  + runSeq(List~StudyGroupDto~, User) void
}
class SequentialQueueProcessor {
  + SequentialQueueProcessor() 
  + submitTask(Runnable) void
  + shutdown() void
}
class SpringTestBoot {
  + SpringTestBoot() 
  + clearAll() void
}
class StudyGroup {
  + StudyGroup() 
  - FormOfEducation formOfEducation
  - Person groupAdmin
  - long expelledStudents
  - Long id
  - long studentsCount
  - Long shouldBeExpelled
  - User creator
  - int transferredStudents
  - LocalDate creationDate
  - Semester semesterEnum
  - LocalDate lastUpdateTime
  - String name
  - Coordinates coordinates
  - float averageMark
  - User lastUpdate
  + hashCode() int
  + toString() String
  + equals(Object) boolean
  # canEqual(Object) boolean
  # onCreate() void
  # onUpdate() void
   Long id
   int transferredStudents
   LocalDate lastUpdateTime
   Person groupAdmin
   FormOfEducation formOfEducation
   Coordinates coordinates
   LocalDate creationDate
   String name
   float averageMark
   User lastUpdate
   long studentsCount
   Semester semesterEnum
   User creator
   Long shouldBeExpelled
   long expelledStudents
}
class StudyGroupAlreadyExistException {
  + StudyGroupAlreadyExistException(String) 
}
class StudyGroupController {
  + StudyGroupController(StudyGroupService) 
  + createStudyGroup(StudyGroupDto) ResponseEntity~Response~StudyGroup~~
  + updateStudyGroupById(Long, StudyGroupDto) ResponseEntity~Response~StudyGroup~~
  + countEducationForm() ResponseEntity~Response~List~Map~String, Long~~~~
  + getFilteredStudyGroups(String, Long, FormOfEducation, Semester, LocalDate, Long, Float, Long, Integer, Person, int, int) ResponseEntity~Response~Page~StudyGroup~~~
  + updateAdmin(Long, Long) ResponseEntity~Response~String~~
  + getStudyGroupUpdates(int, int) Page~StudyGroup~
  + getAllStudyGroups(int, int, String, String) ResponseEntity~Response~Page~StudyGroup~~~
  + deleteStudyGroupAdmin(String) ResponseEntity~Response~String~~
  + deleteStudyGroupById(Long) ResponseEntity~Response~String~~
   ResponseEntity~Response~Integer~~ countOfExpelledStudents
   ResponseEntity~Response~List~Float~~~ uniqueAverageMarks
}
class StudyGroupDto {
  + StudyGroupDto(Long, String, Coordinates, LocalDate, long, long, int, String, Long, float, String, PersonDto) 
  + StudyGroupDto() 
  - PersonDto groupAdmin
  - String formOfEducation
  - Long id
  - long expelledStudents
  - float averageMark
  - LocalDate creationDate
  - long studentsCount
  - Coordinates coordinates
  - int transferredStudents
  - String semesterEnum
  - String name
  - Long shouldBeExpelled
  + hashCode() int
  + builder() StudyGroupDtoBuilder
  + equals(Object) boolean
  # canEqual(Object) boolean
  + toString() String
   String name
   Coordinates coordinates
   LocalDate creationDate
   float averageMark
   long studentsCount
   String semesterEnum
   Long id
   int transferredStudents
   Long shouldBeExpelled
   PersonDto groupAdmin
   String formOfEducation
   long expelledStudents
}
class StudyGroupDtoBuilder {
  ~ StudyGroupDtoBuilder() 
  + id(Long) StudyGroupDtoBuilder
  + name(String) StudyGroupDtoBuilder
  + build() StudyGroupDto
  + groupAdmin(PersonDto) StudyGroupDtoBuilder
  + coordinates(Coordinates) StudyGroupDtoBuilder
  + creationDate(LocalDate) StudyGroupDtoBuilder
  + studentsCount(long) StudyGroupDtoBuilder
  + expelledStudents(long) StudyGroupDtoBuilder
  + toString() String
  + transferredStudents(int) StudyGroupDtoBuilder
  + semesterEnum(String) StudyGroupDtoBuilder
  + formOfEducation(String) StudyGroupDtoBuilder
  + shouldBeExpelled(Long) StudyGroupDtoBuilder
  + averageMark(float) StudyGroupDtoBuilder
}
class StudyGroupExceptionHandler {
  + StudyGroupExceptionHandler() 
  + studyGroupAlreadyExistException(StudyGroupAlreadyExistException) Response~String~
}
class StudyGroupMapper {
  + StudyGroupMapper() 
  + toEntity(StudyGroupDto) StudyGroup
}
class StudyGroupNotExistException {
  + StudyGroupNotExistException(String) 
}
class StudyGroupRepository {
<<Interface>>
  + deleteByAdminName(String) void
  + findAll(Pageable) Page~StudyGroup~
  + deleteByAdminId(String) void
  + updateGroupAdmin(Long, Long) void
   Integer countOfExpelledStudents
   List~Float~ uniqueGroupsByAverageMark
   List~Object[]~ countFormsOfEducations
}
class StudyGroupService {
<<Interface>>
  + filterStudyGroups(String, Long, FormOfEducation, Semester, LocalDate, Long, Float, Long, Integer, Person) List~StudyGroup~
  + updateAdminGroup(Long, Long) void
  + updateStudyGroup(Long, StudyGroupDto) StudyGroup
  + add(StudyGroup) StudyGroup
  + deleteByGroupAdminName(String) void
  + addAll(List~StudyGroup~) List~StudyGroup~
  + add(StudyGroupDto) StudyGroup
  + getAllStudyGroups(Pageable) Page~StudyGroup~
  + deleteStudyGroup(Long) void
  + getPagedResult(List~StudyGroup~, Pageable) Page~StudyGroup~
  + getPageAfterSort(int, int, String, String) Pageable
   Integer countOfExpelledStudents
   List~Float~ uniqueStudyGroupByAverageMark
   List~Map~String, Long~~ countFormsOfEducations
}
class StudyGroupServiceImpl {
  + StudyGroupServiceImpl(StudyGroupRepository, AuthenticationUtils, CoordinatesRepository, PersonService) 
  + getPageAfterSort(int, int, String, String) Pageable
  + addAll(List~StudyGroup~) List~StudyGroup~
  + add(StudyGroupDto) StudyGroup
  + deleteByGroupAdminName(String) void
  + updateAdminGroup(Long, Long) void
  + filterStudyGroups(String, Long, FormOfEducation, Semester, LocalDate, Long, Float, Long, Integer, Person) List~StudyGroup~
  + getAllStudyGroups(Pageable) Page~StudyGroup~
  + updateStudyGroup(Long, StudyGroupDto) StudyGroup
  + getPagedResult(List~StudyGroup~, Pageable) Page~StudyGroup~
  + add(StudyGroup) StudyGroup
  + deleteStudyGroup(Long) void
   Integer countOfExpelledStudents
   List~Float~ uniqueStudyGroupByAverageMark
   List~Map~String, Long~~ countFormsOfEducations
}
class StudyGroupSpecifications {
  + StudyGroupSpecifications() 
  + createdAfter(LocalDate) Specification~StudyGroup~
  + hasStudentsCountGreaterThan(long) Specification~StudyGroup~
  + hasExpelledStudentsGreaterThan(long) Specification~StudyGroup~
  + hasSemester(Semester) Specification~StudyGroup~
  + hasShouldBeExpelledGreaterThan(long) Specification~StudyGroup~
  + hasAverageMarkGreaterThan(float) Specification~StudyGroup~
  + hasFormOfEducation(FormOfEducation) Specification~StudyGroup~
  + hasAdmin(Person) Specification~StudyGroup~
  + hasName(String) Specification~StudyGroup~
  + hasTransferredStudentsGreaterThan(int) Specification~StudyGroup~
}
class User {
  + User(String, String, UserRole) 
  + User() 
  - String password
  - UserRole role
  - String username
  + equals(Object) boolean
  + toString() String
  # canEqual(Object) boolean
  + hashCode() int
  + builder() UserBuilder
   String password
   UserRole role
   String username
   Collection~GrantedAuthority~ authorities
}
class UserAlreadyExist {
  + UserAlreadyExist(String) 
}
class UserBuilder {
  ~ UserBuilder() 
  + username(String) UserBuilder
  + password(String) UserBuilder
  + role(UserRole) UserBuilder
  + build() User
  + toString() String
}
class UserConflictException {
  + UserConflictException(String) 
}
class UserDto {
  + UserDto(String, String, String) 
  + UserDto() 
  - String password
  - String username
  - String role
  + toString() String
  + builder() UserDtoBuilder
  + equals(Object) boolean
  # canEqual(Object) boolean
  + hashCode() int
   String password
   String username
   String role
}
class UserDtoBuilder {
  ~ UserDtoBuilder() 
  + username(String) UserDtoBuilder
  + password(String) UserDtoBuilder
  + role(String) UserDtoBuilder
  + build() UserDto
  + toString() String
}
class UserMapper {
  + UserMapper() 
  + toEntity(UserDto) User
}
class UserNotFoundException {
  + UserNotFoundException(String) 
}
class UserRepository {
<<Interface>>
  + findAllByRole(UserRole) List~User~
  + findByUsername(String) User
}
class UserRole {
<<enumeration>>
  + UserRole() 
  + values() UserRole[]
  + valueOf(String) UserRole
}
class UserService {
<<Interface>>
  + add(User) User
  + getUserByUsername(String) User
  + updateUser(User) User
  + isUserExists(String) boolean
   UserDetailsService userDetailsService
}
class UserServiceImpl {
  + UserServiceImpl(UserRepository) 
  + add(User) User
  + isUserExists(String) boolean
  + updateUser(User) User
  + getUserByUsername(String) User
   UserDetailsService userDetailsService
}
class Utils {
  + Utils() 
  + isEmptyOrNull(String) boolean
}

AdminProcessingServiceImpl  ..>  AdminProcessingService 
AuthServiceImpl  ..>  AuthService 
CoordinatesServiceImpl  ..>  CoordinateService 
ImportProcessingImpl  -->  ImportMode 
ImportProcessing  -->  AsyncServiceUserProcessing 
ImportProcessing  -->  SeqServiceUserProcessing 
ImportProcessingImpl  ..>  ImportProcessing 
ImportServiceImpl  ..>  ImportService 
JwtServiceImpl  ..>  JwtService 
LocationServiceImpl  ..>  LocationService 
OperationServiceImpl  ..>  OperationService 
Person  ..>  CreatorAware 
Person  -->  PersonBuilder 
PersonDto  -->  PersonDtoBuilder 
PersonServiceImpl  ..>  PersonService 
StudyGroup  ..>  CreatorAware 
StudyGroupDto  -->  StudyGroupDtoBuilder 
StudyGroupServiceImpl  ..>  StudyGroupService 
User  -->  UserBuilder 
UserDto  -->  UserDtoBuilder 
UserServiceImpl  ..>  UserService
```
