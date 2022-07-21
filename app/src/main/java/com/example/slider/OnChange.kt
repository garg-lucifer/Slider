package com.example.slider

interface OnChange {
        fun displayToast()
        fun valueStop(slideData: SlideData)
        fun valueStart()
        fun delete(slideData: SlideData)
}