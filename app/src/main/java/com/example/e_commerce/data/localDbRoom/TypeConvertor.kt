package com.example.e_commerce.data.localDbRoom

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@TypeConverters
class TypeConvertor {
@TypeConverter
fun fromAnyToString(attributes: Any?):String{
    if(attributes==null){
        return ""
    }
    else{
        return attributes as String
    }
}

    @TypeConverter
    fun fromStringToAny(attributes:String?):Any{
        if(attributes==null){
            return ""
        }
        else{
            return attributes
        }
    }
}




