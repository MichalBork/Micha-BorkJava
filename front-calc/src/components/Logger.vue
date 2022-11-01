// src/views/SignUp.vue

<template>

  <div v-if="!logged">
    <h1>Sign Up</h1>
    <input type="text" placeholder="Username" v-model="username" />
    <input type="text" placeholder="Password" v-model="password" />

    <input type="button" @click="signUp" value="Sign Up" />
    <p v-if="msg">{{ msg }}</p>
  </div>
  <div v-else>
    <p>You are already logged in.</p>
    <p v-for="(item, index) in role">You are an {{item}}.</p>
  </div>
</template>
<script>
import AuthService from '@/service/AuthService.js';

export default {
  data() {
    return {
      username: '',
      password: '',
      password_repeat: '',
      msg: '',
      logged: false,
      role: [],
    };
  },
  mounted() {
    localStorage.clear()
    localStorage.setItem('logged', 'false');
    console.log(localStorage.getItem('logged'));

  },
  methods: {

    async signUp() {
      try {
        const credentials = {
          name: this.username,
          password: this.password,
        };
        const response = await AuthService.signUp(credentials);
        this.msg = response.msg;
        console.log(localStorage.getItem('logged'));

        switch (true) {
          case response.userPermissions.indexOf(1) !== -1:
            localStorage.setItem('logged', 'true');
            localStorage.setItem('role', 'user');
             window.location.href = '/calc';
          case response.userPermissions.indexOf(2) !== -1:
            localStorage.setItem('logged', 'true');
            localStorage.setItem('role', 'admin');
            window.location.href = '/calendar';

            break;
        }
        console.log(localStorage);
        this.logged = true;
      } catch (error) {
        this.msg = error.response.data.msg;
      }
    },
    getRole( roleId) {
      //if the number is 1, then it is a student
      //if the number is 2, then it is a teacher
      switch (roleId) {
        case roleId.indexOf(1) !== -1:
          return this.role.push('student');
        case roleId.indexOf(2) !== -1:
          return this.role.push("teacher");
      }
      console.log(this.role);
    }
  }
};
</script>