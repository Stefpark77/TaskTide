<template>
  <div class="placement_start" v-if="!this.$store?.state?.showSignUp">
    <div class="left_image_login">
    </div>
    <div class="right_size">
      <div class="placement_end">
        <div class="space_between">
          <div class="placement_center">
            <div class="big_space">
              <div class="title_text">
                Login
              </div>
            </div>
          </div>
          <div class="space">
            <input class="login_signup_input"
                   id="username" type="text" placeholder="Username" v-model="username"/>
          </div>
          <div class="space">
            <input class="login_signup_input"
                   id="password" type="password" placeholder="Password" v-model="password"/>
          </div>
          <div class="big_space">
            <div class="switch_button" v-on:click="signUp">
              New User?
            </div>
          </div>
          <div class="placement_center">
            <v-btn
                rounded="xl"
                size="x-large"
                color="#1e90ff"
                class="standard_button"
                variant="flat"
                @click="login">
              LOGIN
            </v-btn>
          </div>
          <div class="error" v-if="this.$store?.state?.loginError">
            Invalid Username or Password
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  data() {
    return {
      username: this.$store?.state?.username ?? '',
      password: this.$store?.state?.password ?? '',
    };
  },
  watch: {
    username: {
      handler: function (val) {
        this.$store?.commit('setUsername', val);
      },
      deep: true,
    },
    password: {
      handler: function (val) {
        this.$store?.commit('setPassword', val);
      },
      deep: true,
    },
  },
  methods: {
    login() {
      this.$store?.commit('login', true);
    },
    signUp() {
      this.$store?.commit('setShowSignUp', true);
    },
  },

};
</script>

<style>
.left_image_login {
  width: 50%;
  height: 100%;
  left: 0;
  position: absolute;
  background-color: red;
  background-repeat: repeat;
  border-right: 10px solid gray;
  background-image: url("https://media.istockphoto.com/id/1363738494/photo/checking-off-a-to-do-list-on-a-clipboard-with-a-red-check-mark.jpg?s=612x612&w=0&k=20&c=UM_m3kG2TM5jcoNL2RFK4b5tABTsrjBaVC9SgzWasAc=");
}

.right_size {
  position: absolute;
  width: 50%;
  right: 0;
  height: 100%;
  display: flex;
  align-items: center;
}

.placement_start {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
}

.title_text {
  color: dodgerblue;
  margin-bottom: 35px;
  font-weight: bold;
  font-size: xxx-large;
  border-bottom: 3px solid dodgerblue;
  border-radius: 0;
}

.placement_end {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.placement_center {
  display: flex;
  justify-content: center;
}

.switch_button {
  color: dodgerblue;
  margin-bottom: 35px;
  font-weight: bold;
  font-style: italic;
}

.switch_button:hover {
  color: royalblue;
  font-style: normal;
  cursor: pointer;
}

.big_space {
  margin-bottom: 100px;
}

.space {
  margin-bottom: 10px;
}

.space_between {
  margin-left: 250px;
}

.error {
  color: red;
  font-size: small;
  font-style: italic;
  margin-top: 2px;
}

.login_signup_input {
  border: 10px;
  box-shadow: none;
  border-bottom: 1.5px solid gray;
  border-radius: 0;
  outline: none;
  font-size: x-large;
}

.standard_button {
  background-color: dodgerblue;
  color: white;
  padding: 10px 50px;
  font-weight: bold;
  border-radius: 35px;
  justify-content: center;
  font-size: x-large;
  font-family: Arial, Helvetica, sans-serif
}

.standard_button:hover {
  background-color: royalblue;
  outline: none;
  box-shadow: none;
}
</style>