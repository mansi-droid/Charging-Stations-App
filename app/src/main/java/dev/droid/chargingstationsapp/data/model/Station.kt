package dev.droid.chargingstationsapp.data.model

data class Station(
        val AddressInfo : AddressInfo,
        val Connections : List<Connection>,
        val DataProvider : DataProvider,
        val DataProviderID : Int,
        val DataProvidersReference : String,
        val DataQualityLevel : Int,
        val DateCreated : String,
        val DateLastConfirmed : String,
        val DateLastStatusUpdate : String,
        val DateLastVerified : String,
        val DatePlanned : Any,
        val GeneralComments : String,
        val ID : Int,
        val IsRecentlyVerified : Boolean,
        val MediaItems : Any,
        val MetadataValues : Any,
        val NumberOfPoints : String,
        val OperatorID : Int,
        val OperatorInfo : OperatorInfo,
        val OperatorsReference : String,
        val ParentChargePointID : Any,
        val PercentageSimilarity : Any,
        val StatusType : StatusType,
        val StatusTypeID : Int,
        val SubmissionStatus : SubmissionStatus,
        val SubmissionStatusTypeID : Int,
        val UUID : String,
        val UsageCost : String,
        val UsageType : UsageType,
        val UsageTypeID : Int,
        val UserComments : Any
)

data class AddressInfo(
        val AccessComments : String,
        val AddressLine1 : String,
        val AddressLine2 : String,
        val ContactEmail : String,
        val ContactTelephone1 : String,
        val ContactTelephone2 : String,
        val Country : Country,
        val CountryID : Int,
        val Distance : Double,
        val DistanceUnit : Int,
        val ID : Int,
        val Latitude : Double,
        val Longitude : Double,
        val Postcode : String,
        val RelatedURL : String,
        val StateOrProvince : String,
        val Title : String,
        val Town : String
)

data class Country(
        val ContinentCode : String,
        val ID : Int,
        val ISOCode : String,
        val Title : String
)

data class Connection(
        val Amps : Int,
        val Comments : Any,
        val ConnectionType : ConnectionType,
        val ConnectionTypeID : Int,
        val CurrentType : CurrentType,
        val CurrentTypeID : Int,
        val ID : Int,
        val Level : Level,
        val LevelID : Int,
        val PowerKW : Double,
        val Quantity : Int,
        val Reference : Any,
        val StatusType : StatusType,
        val StatusTypeID : Int,
        val Voltage : Int
)

data class ConnectionType(
        val FormalName : String,
        val ID : Int,
        val IsDiscontinued : Boolean,
        val IsObsolete : Boolean,
        val Title : String
)

data class CurrentType(
        val Description : String,
        val ID : Int,
        val Title : String
)

data class Level(
        val Comments : String,
        val ID : Int,
        val IsFastChargeCapable : Boolean,
        val Title : String
)

data class DataProvider(
        val Comments : Any,
        val DataProviderStatusType : DataProviderStatusType,
        val DateLastImported : String,
        val ID : Int,
        val IsApprovedImport : Boolean,
        val IsOpenDataLicensed : Boolean,
        val IsRestrictedEdit : Boolean,
        val License : String,
        val Title : String,
        val WebsiteURL : String
)

data class DataProviderStatusType(
        val ID : Int,
        val IsProviderEnabled : Boolean,
        val Title : String
)

data class OperatorInfo(
        val AddressInfo : Any,
        val BookingURL : Any,
        val Comments : String,
        val ContactEmail : String,
        val FaultReportEmail : String,
        val ID : Int,
        val IsPrivateIndividual : Boolean,
        val IsRestrictedEdit : Boolean,
        val PhonePrimaryContact : String,
        val PhoneSecondaryContact : Any,
        val Title : String,
        val WebsiteURL : String
)

data class StatusType(
        val ID : Int,
        val IsOperational : Boolean,
        val IsUserSelectable : Boolean,
        val Title : String
)

data class SubmissionStatus(
        val ID : Int,
        val IsLive : Boolean,
        val Title : String
)

data class UsageType(
        val ID : Int,
        val IsAccessKeyRequired : Boolean,
        val IsMembershipRequired : Boolean,
        val IsPayAtLocation : Boolean,
        val Title : String
)
