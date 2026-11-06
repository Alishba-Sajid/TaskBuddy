package com.example.mytodoapp.di

import android.content.Context
import androidx.room.Room
import com.example.mytodoapp.data.local.database.TaskDao
import com.example.mytodoapp.data.local.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTaskDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_buddy_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideTaskDao(
        database: TaskDatabase
    ): TaskDao = database.taskDao()
}