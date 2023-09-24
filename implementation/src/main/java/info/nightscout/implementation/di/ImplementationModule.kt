package info.nightscout.implementation.di

import app.aaps.core.main.graph.OverviewData
import app.aaps.interfaces.alerts.LocalAlertUtils
import app.aaps.interfaces.db.PersistenceLayer
import app.aaps.interfaces.iob.GlucoseStatusProvider
import app.aaps.interfaces.logging.LoggerUtils
import app.aaps.interfaces.logging.UserEntryLogger
import app.aaps.interfaces.notifications.NotificationHolder
import app.aaps.interfaces.plugin.ActivePlugin
import app.aaps.interfaces.profile.DefaultValueHelper
import app.aaps.interfaces.profile.ProfileFunction
import app.aaps.interfaces.profile.ProfileUtil
import app.aaps.interfaces.profiling.Profiler
import app.aaps.interfaces.protection.PasswordCheck
import app.aaps.interfaces.protection.ProtectionCheck
import app.aaps.interfaces.pump.BlePreCheck
import app.aaps.interfaces.pump.DetailedBolusInfoStorage
import app.aaps.interfaces.pump.PumpSync
import app.aaps.interfaces.pump.TemporaryBasalStorage
import app.aaps.interfaces.pump.WarnColors
import app.aaps.interfaces.queue.CommandQueue
import app.aaps.interfaces.receivers.ReceiverStatusStore
import app.aaps.interfaces.resources.ResourceHelper
import app.aaps.interfaces.stats.DexcomTirCalculator
import app.aaps.interfaces.stats.TddCalculator
import app.aaps.interfaces.stats.TirCalculator
import app.aaps.interfaces.storage.Storage
import app.aaps.interfaces.ui.IconsProvider
import app.aaps.interfaces.userEntry.UserEntryPresentationHelper
import app.aaps.interfaces.utils.DecimalFormatter
import app.aaps.interfaces.utils.HardLimits
import app.aaps.interfaces.utils.Translator
import app.aaps.interfaces.utils.TrendCalculator
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import info.nightscout.implementation.DefaultValueHelperImpl
import info.nightscout.implementation.HardLimitsImpl
import info.nightscout.implementation.LocalAlertUtilsImpl
import info.nightscout.implementation.TranslatorImpl
import info.nightscout.implementation.TrendCalculatorImpl
import info.nightscout.implementation.UserEntryLoggerImpl
import info.nightscout.implementation.androidNotification.NotificationHolderImpl
import info.nightscout.implementation.db.PersistenceLayerImpl
import info.nightscout.implementation.iob.GlucoseStatusProviderImpl
import info.nightscout.implementation.logging.LoggerUtilsImpl
import info.nightscout.implementation.overview.OverviewDataImpl
import info.nightscout.implementation.plugin.PluginStore
import info.nightscout.implementation.profile.ProfileFunctionImpl
import info.nightscout.implementation.profile.ProfileStoreObject
import info.nightscout.implementation.profile.ProfileUtilImpl
import info.nightscout.implementation.profiling.ProfilerImpl
import info.nightscout.implementation.protection.PasswordCheckImpl
import info.nightscout.implementation.protection.ProtectionCheckImpl
import info.nightscout.implementation.pump.BlePreCheckImpl
import info.nightscout.implementation.pump.DetailedBolusInfoStorageImpl
import info.nightscout.implementation.pump.PumpSyncImplementation
import info.nightscout.implementation.pump.TemporaryBasalStorageImpl
import info.nightscout.implementation.pump.WarnColorsImpl
import info.nightscout.implementation.queue.CommandQueueImplementation
import info.nightscout.implementation.receivers.NetworkChangeReceiver
import info.nightscout.implementation.receivers.ReceiverStatusStoreImpl
import info.nightscout.implementation.resources.IconsProviderImplementation
import info.nightscout.implementation.resources.ResourceHelperImpl
import info.nightscout.implementation.stats.DexcomTirCalculatorImpl
import info.nightscout.implementation.stats.TddCalculatorImpl
import info.nightscout.implementation.stats.TirCalculatorImpl
import info.nightscout.implementation.storage.FileStorage
import info.nightscout.implementation.userEntry.UserEntryPresentationHelperImpl
import info.nightscout.implementation.utils.DecimalFormatterImpl

@Module(
    includes = [
        ImplementationModule.Bindings::class,
        CommandQueueModule::class
    ]
)

@Suppress("unused")
abstract class ImplementationModule {

    @ContributesAndroidInjector abstract fun profileStoreInjector(): ProfileStoreObject
    @ContributesAndroidInjector abstract fun contributesNetworkChangeReceiver(): NetworkChangeReceiver

    @Module
    interface Bindings {

        @Binds fun bindPersistenceLayer(persistenceLayerImpl: PersistenceLayerImpl): PersistenceLayer
        @Binds fun bindActivePlugin(pluginStore: PluginStore): ActivePlugin
        @Binds fun bindOverviewData(overviewData: OverviewDataImpl): OverviewData
        @Binds fun bindUserEntryLogger(userEntryLoggerImpl: UserEntryLoggerImpl): UserEntryLogger
        @Binds fun bindDetailedBolusInfoStorage(detailedBolusInfoStorageImpl: DetailedBolusInfoStorageImpl): DetailedBolusInfoStorage
        @Binds fun bindTemporaryBasalStorage(temporaryBasalStorageImpl: TemporaryBasalStorageImpl): TemporaryBasalStorage
        @Binds fun bindTranslator(translatorImpl: TranslatorImpl): Translator
        @Binds fun bindDefaultValueHelper(defaultValueHelperImpl: DefaultValueHelperImpl): DefaultValueHelper
        @Binds fun bindProtectionCheck(protectionCheckImpl: ProtectionCheckImpl): ProtectionCheck
        @Binds fun bindPasswordCheck(passwordCheckImpl: PasswordCheckImpl): PasswordCheck
        @Binds fun bindLoggerUtils(loggerUtilsImpl: LoggerUtilsImpl): LoggerUtils
        @Binds fun bindProfiler(profilerImpl: ProfilerImpl): Profiler
        @Binds fun bindWarnColors(warnColorsImpl: WarnColorsImpl): WarnColors
        @Binds fun bindHardLimits(hardLimitsImpl: HardLimitsImpl): HardLimits
        @Binds fun bindResourceHelper(resourceHelperImpl: ResourceHelperImpl): ResourceHelper
        @Binds fun bindBlePreCheck(blePreCheckImpl: BlePreCheckImpl): BlePreCheck

        @Binds fun bindTrendCalculatorInterface(trendCalculator: TrendCalculatorImpl): TrendCalculator
        @Binds fun bindTddCalculatorInterface(tddCalculator: TddCalculatorImpl): TddCalculator
        @Binds fun bindTirCalculatorInterface(tirCalculator: TirCalculatorImpl): TirCalculator
        @Binds fun bindDexcomTirCalculatorInterface(dexcomTirCalculator: DexcomTirCalculatorImpl): DexcomTirCalculator
        @Binds fun bindPumpSyncInterface(pumpSyncImplementation: PumpSyncImplementation): PumpSync
        @Binds fun bindLocalAlertUtilsInterface(localAlertUtils: LocalAlertUtilsImpl): LocalAlertUtils
        @Binds fun bindIconsProviderInterface(iconsProvider: IconsProviderImplementation): IconsProvider
        @Binds fun bindNotificationHolderInterface(notificationHolder: NotificationHolderImpl): NotificationHolder
        @Binds fun bindCommandQueue(commandQueue: CommandQueueImplementation): CommandQueue
        @Binds fun bindsProfileFunction(profileFunctionImpl: ProfileFunctionImpl): ProfileFunction
        @Binds fun bindsProfileUtil(profileUtilImpl: ProfileUtilImpl): ProfileUtil
        @Binds fun bindsStorage(fileStorage: FileStorage): Storage
        @Binds fun bindsReceiverStatusStore(receiverStatusStoreImpl: ReceiverStatusStoreImpl): ReceiverStatusStore
        @Binds fun bindsUserEntryPresentationHelper(userEntryPresentationHelperImpl: UserEntryPresentationHelperImpl): UserEntryPresentationHelper
        @Binds fun bindsGlucoseStatusProvider(glucoseStatusProviderImpl: GlucoseStatusProviderImpl): GlucoseStatusProvider
        @Binds fun bindsDecimalFormatter(decimalFormatterImpl: DecimalFormatterImpl): DecimalFormatter
    }
}