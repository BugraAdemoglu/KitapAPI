import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:9090/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

export default {

  getBooks({ filters = {}, sortByRating = false }) {
    const params = new URLSearchParams();

    if (sortByRating) {
      params.append('sort', 'rating');
    }
    if (filters.authorId) {
      params.append('authorId', filters.authorId);
    }
    if (filters.publishHouseId) {
      params.append('publishHouseId', filters.publishHouseId);
    }

    const queryString = params.toString();
    const url = `/books${queryString ? '?' + queryString : ''}`;

    return apiClient.get(url);
  },

  addBook(bookData) {
    return apiClient.post('/books', bookData);
  },

  getAuthors() {
    return apiClient.get('/authors');
  },

  getPublishHouses() {
    return apiClient.get('/publishhouses');
  },


  getUsers() {
    return apiClient.get('/users');
  },


  getBookById(id) {
    return apiClient.get(`/books/${id}`);
  },

  addComment(bookId,userId, commentData) {
    return apiClient.post(`/books/${bookId}/comments/${userId}`, commentData);
  },

  addRate(bookId, rateData) {
    return apiClient.post(`/books/${bookId}/rate`, rateData);
  }
};
