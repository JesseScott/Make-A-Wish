package tt.co.jesses.makeawish.domain.repository

interface SettingsRepository {
    fun getPrefValue(key: String): Boolean
    fun getIntPrefValue(key: String, defaultValue: Int): Int
    fun setPrefValue(key: String, value: Boolean)
    fun setIntPrefValue(key:String, value: Int)
    fun completeOnboarding()
}
