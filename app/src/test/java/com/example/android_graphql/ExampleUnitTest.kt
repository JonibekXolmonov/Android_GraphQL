package com.example.android_graphql

import com.example.android_graphql.networking.GraphQL
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    //all users api
    @Test
    fun responseInNotNull() = runTest {
        val response = GraphQL.get().query(UsersListQuery(10)).execute()
        assertNotNull(response)
    }

    @Test
    fun usersLitNotNull() = runTest {
        val response = GraphQL.get().query(UsersListQuery(10)).execute()
        val users = response.data!!.users
        assertNotNull(users)
    }

    @Test
    fun usersLitSize() = runTest {
        val response = GraphQL.get().query(UsersListQuery(10)).execute()
        val users = response.data!!.users
        assertEquals(users.size, 10)
    }

    @Test
    fun checkSecondUserData() = runTest {
        val response = GraphQL.get().query(UsersListQuery(10)).execute()
        val user2 = response.data!!.users[2]
        assertNotNull(user2)
    }

    //api to get by id
    val id = "f03dba89-9787-4f17-bd35-2c125a34695d"

    @Test
    fun checkUserById() = runTest {
        val response = GraphQL.get().query(UserByIdQuery(id)).execute().data
        assertNotNull(response!!.users_by_pk)
    }

    @Test
    fun checkUserNameById() = runTest {
        val response = GraphQL.get().query(UserByIdQuery(id)).execute().data
        val user = response!!.users_by_pk
        assertEquals(user!!.name, "Jonibek")
    }

    @Test
    fun checkInsertUser() = runTest {
        val response =
            GraphQL.get().mutation(InsertUserMutation("Jonibek", "rocket", "twitter")).execute()
        //assertTrue(response.data!!.insert_users!!.affected_rows == 1)
        assertTrue(!response.hasErrors())
    }

    @Test
    fun checkDeleteUser() = runTest {
        val response = GraphQL.get().mutation(DeleteUserMutation(id)).execute()
        assertTrue(!response.hasErrors())
    }

    @Test
    fun checkUpdateUser() = runTest {
        val response = GraphQL.get().mutation(
            UpdateUserMutation(id, "name2", "rocket", "twitter")
        ).execute()
        assertTrue(!response.hasErrors())
    }
}