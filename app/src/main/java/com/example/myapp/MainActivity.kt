@HiltViewModel
class ChatListViewModel @AssistedInject constructor(
    @Assisted val companyId: Int,
    private val chatRepository: IChatRepository
) : ViewModel() {

    @AssistedFactory
    interface ChatListViewModelFactory {
        fun create(companyId: Int): ChatListViewModel
    }

    var isLoading by mutableStateOf(true)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations

    private val _assignedNumbers = MutableStateFlow<List<String>>(emptyList())
    val assignedNumbers: StateFlow<List<String>> = _assignedNumbers

    init {
        initializeChats()
    }

    private fun initializeChats() {
        viewModelScope.launch {
            chatRepository.chats.collect { cachedChats ->
                if (cachedChats.isEmpty()) {
                    loadConversations()
                } else {
                    isLoading = false
                }
            }
        }

        // Background check on every navigation
        viewModelScope.launch {
            if (chatRepository.chats.value.isNotEmpty()) {
                backgroundUpdate()
            }
        }
    }

    fun loadConversations() {
        chatRepository.getCompanyConversations(companyId, false, "ACTIVE").onEach { result ->
            when (result) {
                is Resource.Loading -> isLoading = true
                is Resource.Success -> {
                    _conversations.value = result.data
                    isLoading = false
                }
                is Resource.Error -> {
                    // Optionally show error toast/snackbar
                    isLoading = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshChats() {
        chatRepository.getCompanyConversations(companyId, false, "ACTIVE").onEach { result ->
            when (result) {
                is Resource.Loading -> isRefreshing = true
                is Resource.Success -> {
                    _conversations.value = result.data
                    isRefreshing = false
                }
                is Resource.Error -> {
                    isRefreshing = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun backgroundUpdate() {
        chatRepository.getCompanyConversations(companyId, false, "ACTIVE").onEach { result ->
            if (result is Resource.Success) {
                _conversations.value = result.data
            }
        }.launchIn(viewModelScope)
    }

    fun getAssignedPhoneNumbers(crmid: Long, companyId: Int) {
        viewModelScope.launch {
            chatRepository.getAssignedPhoneNumbers(crmid, companyId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _assignedNumbers.value = result.data.mapNotNull { it.phoneNumber }
                    }
                    is Resource.Error -> {
                        // Handle API failure (optional)
                    }
                    is Resource.Loading -> {
                        // You can add a loading indicator if needed
                    }
                }
            }
        }
    }
}
