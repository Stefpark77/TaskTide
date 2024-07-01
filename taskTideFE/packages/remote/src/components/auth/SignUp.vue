<template>
  <div class="placement_start" v-if="this.$store?.state?.showSignUp">
    <div class="left_image_signup">
    </div>
    <div class="right_size" v-if="this.$store?.state?.showSignUp">
      <div class="space_between">
        <div class="placement_center">
          <div class="big_space">
            <div class="title_text">
              Sign Up
            </div>
          </div>
        </div>
        <div class="space">
          <input class="login_signup_input"
                 id="firstName" type="text" placeholder="First Name" v-model="addFirstName"/>
        </div>
        <div class="space">
          <input class="login_signup_input"
                 id="lastName" type="text" placeholder="Last Name" v-model="addLastName"/>
        </div>
        <div class="space">
          <input class="login_signup_input"
                 id="email" type="text" placeholder="Email" v-model="addEmail"/>
        </div>
        <div class="space">
          <input class="login_signup_input"
                 id="username" type="text" placeholder="Username" v-model="addUsername"/>
        </div>
        <div class="space">
          <input class="login_signup_input"
                 id="password" type="password" placeholder="Password" v-model="addPassword"/>
        </div>

        <div class="space">
          <input class="login_signup_input"
                 id="password" type="password" placeholder="Confirm Password" v-model="addCPassword"/>
        </div>

        <div class="big_space">
          <div class="switch_button" v-on:click="notSignUp">
            Already got an account?
          </div>
        </div>
        <div class="placement_center">
          <v-btn
              rounded="xl"
              size="x-large"
              color="#1e90ff"
              class="standard_button"
              variant="flat"
              @click="signUp">
            CREATE ACCOUNT
          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addFirstName: this.$store?.state?.addFirstName ?? '',
      addLastName: this.$store?.state?.addLastName ?? '',
      addUsername: this.$store?.state?.addUsername ?? '',
      addPassword: this.$store?.state?.addPassword ?? '',
      addCPassword: this.$store?.state?.addCPassword ?? '',
      addEmail: this.$store?.state?.addEmail ?? '',
    };
  },
  watch: {
    addFirstName: {
      handler: function (val) {
        this.$store?.commit('setAddFirstName', val);
      },
      deep: true,
    },
    addLastName: {
      handler: function (val) {
        this.$store?.commit('setAddLastName', val);
      },
      deep: true,
    },
    addUsername: {
      handler: function (val) {
        this.$store?.commit('setAddUsername', val);
      },
      deep: true,
    },
    addPassword: {
      handler: function (val) {
        this.$store?.commit('setAddPassword', val);
      },
      deep: true,
    },
    addCPassword: {
      handler: function (val) {
        this.$store?.commit('setAddCPassword', val);
      },
      deep: true,
    },
    addEmail: {
      handler: function (val) {
        this.$store?.commit('setAddEmail', val);
      },
      deep: true,
    },
  },
  methods: {
    signUp() {
      this.$store?.commit('signUp', {
        firstName: this.addFirstName,
        lastName: this.addLastName,
        username: this.addUsername,
        password: this.addPassword,
        cPassword: this.addCPassword,
        email: this.addEmail
      });
      this.$store?.commit('setShowSignUp', true);
    },
    notSignUp() {
      this.$store?.commit('setShowSignUp', false);
    },
  },
};
</script>


<style>

.left_image_signup {
  width: 50%;
  height: 100%;
  left: 0;
  position: absolute;
  background-color: red;
  background-repeat: repeat;
  border-right: 10px solid gray;
  background-image: url("https://media.istockphoto.com/id/1469214984/photo/welcome-written-speech-bubble-and-blue-megaphone-on-blue-background.webp?b=1&s=170667a&w=0&k=20&c=uxjvmYQaojVUxMSxJBaunTYvp1g5Zuco1YMq55W6s2o=");
}
</style>