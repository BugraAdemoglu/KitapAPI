<template>
  <div>
    <div class="page-header">
      <h1>Kitaplar</h1>
      <p>Aşağıdaki filtreleri kullanarak aradığınız kitapları bulun.</p>
    </div>

    <div class="filters-container">
      <div class="filter-group">
        <label for="author-filter">Yazara Göre Filtrele:</label>
        <select id="author-filter" v-model="selectedAuthor">
          <option :value="null">Tüm Yazarlar</option>
          <option v-for="author in authors" :key="author.id" :value="author.id">{{ author.name }}</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="publisher-filter">Yayınevine Göre Filtrele:</label>
        <select id="publisher-filter" v-model="selectedPublisher">
          <option :value="null">Tüm Yayınevleri</option>
          <option v-for="publisher in publishers" :key="publisher.id" :value="publisher.id">{{ publisher.name }}</option>
        </select>
      </div>

      <div class="actions-group">
        <label class="sort-toggle">
          <input type="checkbox" v-model="sortByRating" />
          Puana Göre Sırala
        </label>
        <button @click="clearFilters" class="clear-button">Filtreleri Temizle</button>
      </div>
    </div>

    <div v-if="isLoading" class="loading">Yükleniyor...</div>
    <div v-else-if="!books.length" class="no-books">
      <p>Bu kriterlere uygun kitap bulunamadı.</p>
    </div>

    <div v-else class="book-grid">
      <RouterLink
        v-for="book in books"
        :key="book.id"
        :to="{ name: 'book-detail', params: { id: book.id, userId: userId } }"
        class="book-preview-card"
      >
        <div class="card-content">
          <h3 class="title">{{ book.title }}</h3>
          <p class="author">{{ book.authorName }}</p>
        </div>
        <div class="card-footer">
          <p class="publisher">{{ book.publishHouseName }}</p>
          <div class="rating-display">
            <StarRating
              :model-value="Math.round(book.averageRating)"
              class="preview-stars"
              style="pointer-events: none;"
            />
            <span class="rating-value">({{ book.averageRating.toFixed(1) }})</span>
          </div>
        </div>
      </RouterLink>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import api from '@/services/api';
import { RouterLink } from 'vue-router';
import StarRating from '@/components/StarRating.vue';
import { useRoute } from 'vue-router';

const route = useRoute();


const books = ref([]);
const isLoading = ref(true);
const authors = ref([]);
const publishers = ref([]);
const selectedAuthor = ref(null);
const selectedPublisher = ref(null);
const sortByRating = ref(false);
const userId= route.params.userId;


import { useUserStore } from '@/stores/user';

const userStore = useUserStore();

const fetchBooks = async () => {
  isLoading.value = true;
  try {
    const filters = {
      authorId: selectedAuthor.value,
      publishHouseId: selectedPublisher.value
    };

    const response = await api.getBooks({ filters, sortByRating: sortByRating.value });
    books.value = response.data;
  } catch (error) {
    console.error("Kitaplar alınamadı:", error);
  } finally {
    isLoading.value = false;
  }
};

const clearFilters = () => {
  selectedAuthor.value = null;
  selectedPublisher.value = null;
  sortByRating.value = false;
};

onMounted(async () => {
  try {
    const authorsResponse = await api.getAuthors();
    authors.value = authorsResponse.data;
    const publishersResponse = await api.getPublishHouses();
    publishers.value = publishersResponse.data;
  } catch (error) {
    console.error("Filtre verileri alınamadı:", error);
  }
  fetchBooks();
  userStore.currentUser = route.params.userId;
});


watch([selectedAuthor, selectedPublisher, sortByRating], fetchBooks);
</script>

<style scoped>

.filters-container { display: flex; flex-wrap: wrap; gap: 1.5rem; background-color: #1e1e1e; padding: 1.5rem; border-radius: 8px; margin-bottom: 2rem; align-items: flex-end; border: 1px solid #333; }
.filter-group { flex: 1; min-width: 200px; }
.filter-group label { display: block; margin-bottom: 0.5rem; color: #bdbdbd; font-weight: 500; }
.filter-group select { width: 100%; padding: 0.8rem; background-color: #333; color: #fff; border: 1px solid #444; border-radius: 4px; }
.actions-group { display: flex; align-items: center; gap: 1.5rem; margin-left: auto; }
.sort-toggle { display: flex; align-items: center; gap: 0.5rem; color: #bdbdbd; cursor: pointer; white-space: nowrap; }
.clear-button { padding: 0.8rem 1.5rem; background-color: #757575; color: white; border: none; border-radius: 4px; cursor: pointer; }
.clear-button:hover { background-color: #616161; }


.page-header { text-align: center; margin-bottom: 2rem; }
.page-header h1 { color: #f5f5f5; }
.page-header p { color: #a0a0a0; }


.book-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 1.5rem; }
.book-preview-card { display: flex; flex-direction: column; justify-content: space-between; background-color: #fff; border-radius: 8px; text-decoration: none; color: #333; border: 1px solid #333; transition: transform 0.2s, box-shadow 0.2s; }
.book-preview-card:hover { transform: translateY(-5px); box-shadow: 0 8px 16px rgba(0,0,0,0.1); }
.card-content { padding: 1.5rem 1.5rem 0 1.5rem; }
.card-footer { padding: 1rem 1.5rem 1.5rem 1.5rem; border-top: 1px solid #eee; }
.title { font-size: 1.25rem; margin-top: 0; }
.author { font-weight: 500; }
.publisher { font-size: 0.9rem; color: #7f8c8d; margin: 0; }


.rating-display { display: flex; align-items: center; justify-content: space-between; margin-top: 0.75rem; }
.preview-stars { font-size: 1rem; color: #fdd835; }
.rating-value { font-size: 0.9rem; font-weight: bold; color: #666; }


.no-books, .loading { text-align: center; padding: 3rem; background-color: #1e1e1e; border-radius: 8px; border: 1px solid #333; grid-column: 1 / -1; }


@media (max-width: 900px) { .book-grid { grid-template-columns: repeat(2, 1fr); } }
@media (max-width: 768px) { .actions-group { margin-left: 0; width: 100%; justify-content: space-between; } }
@media (max-width: 600px) { .book-grid { grid-template-columns: 1fr; } }
</style>
