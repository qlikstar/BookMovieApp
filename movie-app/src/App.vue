<template>
  <v-app>
    <v-toolbar app dark color="black">
      <v-toolbar-title class="headline">
        <router-link
        to='/'
        tag='span'
        style='cursor: pointer'>
        <v-img
                  src="https://www.twilio.com/marketing/bundles/company-brand/img/logos/red/twilio-logo-red.png"
                  lazy-src="Twilio Theater"
                  height=50
                  width=180
                ></v-img>
        </router-link>
      </v-toolbar-title>

        <v-toolbar-title class="headline">Theater</v-toolbar-title>
      <v-spacer></v-spacer>
      <!-- <v-flex xs12 sm3 md6> -->
        <v-chip color="green" text-color="white" v-if="loggedIn">
          <v-avatar>
            <v-icon>account_circle</v-icon>
          </v-avatar>
          Sanket Mishra (415) 640 0312
        </v-chip>
        <!-- </v-flex> -->
        <div class="text-xs-center" >
          <v-chip color="red" text-color="white" @click="logOff()" v-if="loggedIn">Logout</v-chip>
        </div>
    </v-toolbar>

    <v-content>
    <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  components: {
  },
  data: {
    loggedIn: false,
    user: '',
    phoneNumber: ''
  },
  methods: {
    searchMovie () {
      this.$router.push('/search/' + this.searchString)
      this.searchString = ''
    },
    logOff () {
      console.log ('User Logged off')
      localStorage.loggedIn = false
      this.loggedIn = localStorage.loggedIn
      localStorage.user = ''
      localStorage.phoneNumber = ''
      this.$router.push('/')
    }
  },
  computed: {
    dataAvailable () {
      return this.searchString !== null && this.searchString !== ''
    }
  },
  mounted () {
    this.loggedIn = localStorage.loggedIn
    this.user = localStorage.user
    this.phoneNumber = localStorage.phoneNumber
  },
  watch: {
    loggedIn (state) {
      localStorage.loggedIn = state
      this.loggedIn = localStorage.loggedIn
    },
    user (state) {
      localStorage.user = state
      this.user = state
    },
    phoneNumber (state) {
      localStorage.phoneNumber = state
      this.phoneNumber = state
    }
  }
}
</script>
