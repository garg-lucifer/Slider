package com.example.slider

class SlideRepository(private val dao : SlideDataDao) {
    val slideData = dao.getData()

    suspend fun insert(slideData: SlideData){
        dao.insertSlideData(slideData)
    }

    suspend fun update(slideData: SlideData){
        dao.updateSlideData(slideData)
    }

    suspend fun delete(slideData: SlideData){
        dao.deleteSlideData(slideData)
    }

    suspend fun getSize() : Int{
        return dao.getCount()
    }

    suspend fun getNext(startInt : Int) : SlideData{
        return dao.getNext(startInt)
    }

    suspend fun getPrev(startInt : Int) : SlideData{
        return dao.getPrev(startInt)
    }

    suspend fun getNextSize(startInt: Int) : Int{
        return dao.getNextSize(startInt)
    }

}