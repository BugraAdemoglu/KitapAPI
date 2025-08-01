<template>
  <div class="add-book-form">
    <button class="close-button" @click="goHome">×</button>
    <h2>Yeni Kitap Ekle</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="title">Başlık</label>
        <input id="title" type="text" v-model="book.title" required placeholder="Kitap adını girin..." />
      </div>

      <div class="form-grid">
        <div class="form-group">
          <label for="author">Yazar</label>
          <select id="author" v-model="book.authorId" required>
            <option disabled value="">Lütfen bir yazar seçin</option>
            <option v-for="author in authors" :key="author.id" :value="author.id">
              {{ author.name }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label for="publishHouse">Yayınevi</label>
          <select id="publishHouse" v-model="book.publishHouseId" required>
            <option disabled value="">Lütfen bir yayınevi seçin</option>
            <option v-for="house in publishHouses" :key="house.id" :value="house.id">
              {{ house.name }}
            </option>
          </select>
        </div>
      </div>

      <div class="form-group">
        <label for="publicationYear">Yayın Yılı</label>
        <input id="publicationYear" type="number" v-model.number="book.publicationYear" required placeholder="YYYY" />
      </div>

      <div class="form-group">
        <label for="description">Açıklama</label>
        <textarea id="description" v-model="book.description" rows="4" required placeholder="Kitap hakkında kısa bir açıklama..."></textarea>
      </div>

      <button type="submit">Kitabı Ekle</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '../services/api';

const router = useRouter();
const emit = defineEmits(['book-added']);

const goHome = () => {
  router.push('/');
};

const initialBookState = {
  title: '',
  description: '',
  publicationYear: new Date().getFullYear(),
  authorId: '',
  publishHouseId: ''
};
const book = ref({ ...initialBookState });

const authors = ref([]);
const publishHouses = ref([]);

onMounted(async () => {
  try {
    const authorsResponse = await api.getAuthors();
    authors.value = authorsResponse.data;

    const publishHousesResponse = await api.getPublishHouses();
    publishHouses.value = publishHousesResponse.data;
  } catch (error) {
    console.error("Yazarlar veya yayınevleri alınamadı:", error);
    alert('Form verileri yüklenemedi. Backend\'in çalıştığından ve /api/authors, /api/publishhouses endpointlerinin olduğundan emin olun.');
  }
});

const handleSubmit = async () => {
  try {
    await api.addBook(book.value);
    emit('book-added');
    book.value = { ...initialBookState };
    alert('Kitap başarıyla eklendi!');
  } catch (error) {
    console.error("Kitap eklenirken hata oluştu:", error);
  }
};
</script>

<style scoped>
.add-book-form {
  position: relative;
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  color: #333;
}

.add-book-form h2 {
  margin-top: 0;
  text-align: center;
  color: #2c3e50;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #495057;
}

input, select, textarea {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #333;
}

button {
  width: 100%;
  padding: 1rem;
  background-color: #1e1e1e;
  color: white;
  border: 1px solid #444;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  font-size: 1rem;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #333;
}

.close-button {
  position: absolute;
  top: 30px;
  right: -385px;
  background: transparent;
  border: none;
  font-size: 3.0rem;
  color: #aaa;
  cursor: pointer;
  line-height: 0;
  padding: 0;
  transition: color 0.2s ease;
}
.close-button:hover {
  color: #333;
}
</style>
