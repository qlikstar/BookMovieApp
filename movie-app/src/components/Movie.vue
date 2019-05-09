<template>
  <v-container v-if="loading">
    <div class="text-xs-center">
        <v-progress-circular
          indeterminate
          :size="150"
          :width="8"
          color="green">
        </v-progress-circular>
      </div>
  </v-container>

  <v-container v-else>
    <v-layout align-space-around column>
      <v-flex xs12 mr-2 ml-2>
        <v-card>
          <v-img
            :src="singleMovie.Poster"
            aspect-ratio="3"
          ></v-img>
          <v-card-title primary-title>
            <div>
              <h2 class="headline mb-0">{{singleMovie.Title}}-{{singleMovie.Year}}</h2>
              <p>{{ singleMovie.Plot}} </p>
              <h3>Actors:</h3>{{singleMovie.Actors}}
               <h4>Awards:</h4> {{singleMovie.Awards}}
               <p>Genre: {{singleMovie.Genre}}</p>
              <v-card-actions class="justify-center">
                <v-btn block color="red" dark @click="bookTicket(singleMovie.Title)" :disabled="booked">Book Ticket</v-btn>
              </v-card-actions>
              <h3 style="color:green; text-align:center">{{successMessage}}</h3>
            </div>
          </v-card-title>
          <!-- <v-card-actions>
            <v-btn flat color="green" @click="bookTicket()">Book Ticket</v-btn>
          </v-card-actions> -->
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import movieApi from '@/services/MovieApi'
export default {
  props: ['id'],

  data () {
    return {
      singleMovie: '',
      dialog: false,
      loading: true,
      ratings: '',
      booked: false,
      successMessage: ''
    }
  },

  mounted () {
    movieApi.fetchSingleMovie(this.id)
      .then(response => {
        this.singleMovie = response
        this.ratings = this.singleMovie.Ratings
        this.ratings.forEach(function (element) {
          element.Value = parseFloat(element.Value.split(/\/|%/)[0])
          element.Value = element.Value <= 10 ? element.Value / 2 : element.Value / 20
        }
        )
        this.loading = false
      })
      .catch(error => {
        console.log(error)
      })
  },
  methods: {
    bookTicket (title) {
      console.log(title)
      movieApi.bookTicket(localStorage.phoneNumber, localStorage.user, title).then ((response) => {
        this.booked = true
        this.successMessage = 'You have successfully booked tickets. Please check your registered mobile device.'
      })
    }
  }
}

</script>

<style lang="stylus" scoped>
  .v-progress-circular
    margin: 1rem
</style>
