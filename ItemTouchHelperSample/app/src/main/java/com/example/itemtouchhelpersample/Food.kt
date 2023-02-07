package com.example.itemtouchhelpersample

import android.os.Parcel
import android.os.Parcelable

data class Food(
    val id: Int,
    val name: String,
    val kcal: Int,
) : Parcelable {
    
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    )
    
    override fun describeContents(): Int = 0
    
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeInt(id)
        dest.writeInt(kcal)
    }
    
    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }
        
        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}

fun getFoodMock(): List<Food> {
    return listOf(
        Food(1, "Pizza", 736),
        Food(2, "Chicken", 433),
        Food(3, "Noodle", 693),
        Food(4, "Croissant", 233),
        Food(5, "Sandwich", 430),
        Food(6, "Pasta", 1192),
        Food(7, "Ramen", 572),
        Food(8, "Dumpling", 118),
        Food(9, "Pancake", 973),
        Food(10, "Sausage", 400),
        Food(11, "Watermelon", 85),
        Food(12, "Peach", 102),
        Food(13, "Mango", 99),
        Food(14, "Beef", 630),
        Food(15, "Pork", 553),
        Food(16, "Seaweed", 553),
        Food(17, "Sushi", 99),
        Food(18, "Cupcake", 630),
        Food(19, "Ice cream", 553),
        Food(20, "Americano", 99),
        Food(21, "Hamburger", 630),
        Food(22, "Nugget", 553),
        Food(23, "Coke", 99),
        Food(24, "Sprite", 630),
        Food(25, "Ginger Tea", 553),
    )
}