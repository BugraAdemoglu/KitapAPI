import { createRouter, createWebHistory } from 'vue-router'
import BooksView from '../views/BooksView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'books',
      component: BooksView
    },
    {
      path: '/:userId',
      name: 'books',
      component: BooksView
    },
    {
      path: '/add',
      name: 'add-book',

      component: () => import('../views/AddBookView.vue')
    },
    {

      path: '/book/:id/:userId',
      name: 'book-detail',
      component: () => import('../views/BookDetailView.vue')
    }
  ]
})

export default router
