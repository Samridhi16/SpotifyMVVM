package com.sam.spotifymvvm.exoplayer

import android.support.v4.media.MediaMetadataCompat

class FirebaseMusicSource   {

    var songs = emptyList<MediaMetadataCompat>()

//    suspend fun

    private val onReadyListeners = mutableListOf<(Boolean)-> Unit>()

    private var state = State.STATE_CREATED

        set(value){
            if(value == State.STATE_INITIALIZED || value == State.STATE_ERROR){
                synchronized(onReadyListeners){
                    field = value
                    onReadyListeners.forEach {
                        listner -> listner(state == State.STATE_INITIALIZED)
                    }
                }
            }else{
                field = value
            }
        }
    fun whenReady(action: (Boolean)->Unit):Boolean{
        if(state == State.STATE_CREATED || state == State.STATE_INITIALIZING){
            onReadyListeners +=action
            return false
        }else{
            action(state == State.STATE_INITIALIZED)
            return true
        }
    }
}

enum class State{
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR
}