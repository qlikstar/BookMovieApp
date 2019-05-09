<template>
    <v-layout align-center justify-center>
    <v-flex xs12 sm8 md5>
  <v-form v-model="valid">
    <v-container fluid> 

    <v-toolbar dark color="grey darken-3">
    <v-toolbar-title>Please Login</v-toolbar-title>
  </v-toolbar>
      <v-layout>
        <v-flex
          xs3
          md9
        >
        <v-chip
          v-model="chip2"
          close
          color="red"
          text-color="white"
          v-if="error"
        >{{errorMessage}}</v-chip>
        </v-flex>
      </v-layout>

      <v-layout>
        <v-flex
          xs12
          md6
        >
          <v-text-field
            v-model="firstname"
            :rules="nameRules"
            :counter="10"
            label="Firstname"
            required
            :disabled="userDetailsDisabled"
          ></v-text-field>
        </v-flex>
        <v-flex
          xs12
          md6
        >
          <v-text-field
            v-model="lastname"
            :rules="nameRules"
            :counter="10"
            label="Lastname"
            required
            :disabled="userDetailsDisabled"
          ></v-text-field>
        </v-flex>
      </v-layout>
      <v-layout>
        <v-flex
          xs12
          md9
        >
          <v-text-field
            v-model="phoneNumber"
            :rules="phoneNumberRules"
            :counter="10"
            label="Phone Number"
            required
            :disabled="userDetailsDisabled"
          ></v-text-field>
        </v-flex>

        <v-flex
          xs12
          md3
        >
          <v-btn color="warning"
            @click="registerUser(firstname, lastname, phoneNumber)"
            :disabled="userDetailsDisabled"
          >Get SMS</v-btn>
        </v-flex>
      </v-layout>
      <v-layout>
        <v-flex
          xs12
          md9
        >
          <v-text-field
            v-model="otp"
            :rules="otpRules"
            :counter="6"
            label="Enter 6 digit OTP"
            required
            :disabled="otpSubmitDisabled"
          ></v-text-field>
        </v-flex>

        <v-flex
          xs12
          md3
        >
          <v-btn color="red" :disabled="otpSubmitDisabled"
          @click="verifyUser(phoneNumber, otp)">Submit</v-btn>
        </v-flex>
      </v-layout>
    </v-container>
  </v-form>
    </v-flex>
    </v-layout>
</template>

<script>
import movieApi from '@/services/MovieApi'
export default {
  data () {
    return {
      valid: false,
      firstname: '',
      lastname: '',
      userDetailsDisabled: false,
      otpSubmitDisabled: true,
      error: false,
      errorMessage: '',
      nameRules: [
        v => !!v || 'Name is required',
        v => v.length <= 20 || 'Name must be less than 20 characters'
      ],
      phoneNumber: '',
      phoneNumberRules: [
        v => !!v || 'Phone number is required',
        v => /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/ || 'Phone number must be valid'
      ],
      otp: '',
      otpRules: [
        v => !!v || 'OTP is required',
        v => /^\d{6}$/ || 'OTP must be of 6 digits'
      ]
    }
  },
  methods: {
    registerUser (firstname, lastname, phoneNumber) {
      movieApi.sendOTPToDevice(phoneNumber).then ((response) => {
        if (response) {
          console.log(firstname, lastname, phoneNumber)
          this.otpSubmitDisabled = false
          this.userDetailsDisabled = true
        }else {
          this.firstname = ''
          this.lastname = ''
          this.phoneNumber = ''
        }
      })
    },
    verifyUser (phoneNumber, otp) {
      movieApi.verifyOTPToken (phoneNumber, otp).then((response) => {
        if (response) {
          console.log(phoneNumber, otp)
          localStorage.loggedIn = true
          localStorage.user = this.firstname + ' ' + this.lastname
          localStorage.phoneNumber = this.phoneNumber
          this.$router.push('/latest')
        } else {
          this.firstname = ''
          this.lastname = ''
          this.phoneNumber = ''
          this.otp = ''
          this.userDetailsDisabled = false
          this.otpSubmitDisabled = true
          this.error = true
          this.errorMessage = 'OTP does not match with the phone number. Please try again!'
        }
      })
    }
  },
  mounted () {
    if (localStorage.getItem('reloaded')) {
      // The page was just reloaded. Clear the value from local storage
      // so that it will reload the next time this page is visited.
      localStorage.removeItem('reloaded')
    } else {
      // Set a flag so that we know not to reload the page twice.
      localStorage.setItem('reloaded', '1')
      location.reload()
    }
  }
}
</script>
