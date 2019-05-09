import axios from 'axios'

export default {

  fetchMovieCollection (name) {
    return axios.get('&s=' + name)
      .then(response => {
        return response.data
      })
  },

  fetchSingleMovie (id) {
    return axios.get('&i=' + id)
      .then(response => {
        return response.data
      })
  },

  bookTicket (phone, name, movie) {
    return axios.get('http://localhost:9090/sms/book?phone='+ phone +'&name='+ name + '&movie=' + movie)
      .then(response => {
        return response.data
      })
  },

  sendOTPToDevice (phone) {
    return axios.get('http://localhost:9090/sms/otp?phone=' + phone)
      .then(response => {
        return response.data
      })
  },

  verifyOTPToken (phone, otp) {
    return axios.get('http://localhost:9090/verify?phone=' + phone + '&otp=' + otp)
      .then(response => {
        return response.data
      })
  },


}
