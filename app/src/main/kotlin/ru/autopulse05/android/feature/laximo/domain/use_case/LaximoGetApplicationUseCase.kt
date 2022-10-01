package ru.autopulse05.android.feature.laximo.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.autopulse05.android.feature.laximo.data.remote.LaximoRemoteService
import ru.autopulse05.android.feature.laximo.data.remote.dto.LaximoApplicationDto
import ru.autopulse05.android.feature.laximo.data.remote.dto.toLaximoCategory
import ru.autopulse05.android.feature.laximo.domain.model.LaximoApplicationInfo
import ru.autopulse05.android.feature.laximo.domain.model.LaximoCategory
import ru.autopulse05.android.shared.domain.util.Data

class LaximoGetApplicationUseCase(
    private val remoteService: LaximoRemoteService
) {

    operator fun invoke(
        login: String,
        password: String,
        catalog: String,
        ssd: String,
        oem: String,
        locale: String
    ): Flow<Data<MutableMap<String,MutableMap<String,MutableList<LaximoApplicationInfo>>>>> = flow {
        try {
            emit(Data.Loading())
            Log.d("TAG","FFF $catalog $oem")
            val categories = remoteService
                .getApplication(
                    login = login,
                    password = password,
                    ssd = ssd,
                    catalog = catalog,
                    oem = oem,

                )
            val map = mutableMapOf<String,MutableMap<String,MutableList<LaximoApplicationInfo>>>()
            for(i in categories) {
                if(map.containsKey(i.brand)) {
                    var tmp = map[i.brand]
                    if(tmp==null) tmp = mutableMapOf()
                    var list = tmp[i.name.orEmpty()];
                    if(list==null) list = mutableListOf()
                    list.add(LaximoApplicationInfo(
                        options = i.options.orEmpty(),
                        desc = i.description.orEmpty(),
                        period = i.period.orEmpty(),
                        model = i.model.orEmpty()
                    ))
                    tmp[i.name.orEmpty()] = list;
                    map[i.brand.orEmpty()] = tmp
                } else {
                    val tmp = mutableMapOf<String,MutableList<LaximoApplicationInfo>>()
                    val list = mutableListOf<LaximoApplicationInfo>()
                    list.add(LaximoApplicationInfo(
                        options = i.options.orEmpty(),
                        desc = i.description.orEmpty(),
                        period = i.period.orEmpty(),
                        model = i.model.orEmpty()
                    ))
                    tmp[i.name.orEmpty()] = list;
                    map[i.brand.orEmpty()] = tmp
                }
            }
            emit(Data.Success(value = map))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Data.Error(message = e.message))
        }
    }
}
