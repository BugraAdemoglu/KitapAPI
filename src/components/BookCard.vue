<template>
  <div class="book-card">
    <button @click="goBackToBooksList" class="close-button" title="Listeye Geri Dön">
      &times;
    </button>

    <div class="book-details">
      <h3>{{ book.title }} <span class="year">({{ book.publicationYear }})</span></h3>
      <p class="author"><strong>Yazar:</strong> {{ book.authorName }}</p>
      <p class="publish-house"><strong>Yayınevi:</strong> {{ book.publishHouseName }}</p>

      <div class="rating-display">
        <strong>Genel Puan:</strong>
        <StarRating :model-value="Math.round(book.averageRating)" style="pointer-events: none;" />
        <span>({{ book.averageRating.toFixed(1) }})</span>
      </div>

      <p class="description">{{ book.description }}</p>
    </div>

    <div class="actions-section">
      <form @submit.prevent="submitReview" class="review-form">
        <h4>Değerlendirme Yap</h4>
        <div class="form-group">
          <label>Puanınız:</label>
          <StarRating v-model="review.score" />
        </div>
        <div class="form-group">
          <label>Yorumunuz:</label>
          <textarea v-model="review.text" placeholder="Bu kitap hakkındaki düşünceleriniz..."></textarea>
        </div>
        <button
          type="submit"
          :disabled="review.score === 0 && !review.text.trim()"
        >
          Değerlendir
        </button>
      </form>

      <div class="comments-section">
        <h4>Yapılan Yorumlar</h4>
        <div v-if="book.comments && book.comments.length > 0">
          <div v-for="comment in book.comments" :key="comment.id" class="comment">
            <div class="comment-header">
              <strong>{{ comment.userName || 'Anonim' }}</strong>
              <StarRating
                v-if="comment.userScore > 0"
                :model-value="comment.userScore"
                class="comment-stars"
                style="pointer-events: none;"
              />
            </div>
            <p class="comment-text">{{ comment.text }}</p>
          </div>
        </div>
        <div v-else class="no-comments">
          <p>Henüz hiç yorum yapılmamış.</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import api from '../services/api';
import StarRating from './StarRating.vue';
import { useUserStore } from '../stores/user';

const props = defineProps({
  book: { type: Object, required: true },
  userId: { type: Object, required: true }
});

const emit = defineEmits(['action-completed']);

const router = useRouter();
const userStore = useUserStore();

const review = reactive({
  score: 0,
  text: ''
});


const submitReview = async () => {
  userStore.currentUser;
  if (!userStore.currentUser) {
    return;
  }


  if (review.score === 0 && !review.text.trim()) {
    alert("Lütfen puan verin veya bir yorum yazın.");
    return;
  }

  try {

    await api.addComment(props.book.id,userStore.currentUser, {
      text: review.text,
      score: review.score
    });

    alert('Değerlendirmeniz için teşekkürler!');


    review.score = 0;
    review.text = '';


    emit('action-completed');

  } catch (error) {
    alert('Değerlendirme gönderilirken bir hata oluştu.');
    console.error(error);
  }
};


const goBackToBooksList = () => {
  router.push({ name: 'books' });
};
</script>


<style scoped>
.book-card { position: relative; background: #fff; border-radius: 8px; padding: 1.5rem; margin-bottom: 1.5rem; box-shadow: 0 4px 6px rgba(0,0,0,0.1); color: #333; }
.close-button { position: absolute; top: 25px; right: -380px; background: transparent; border: none; font-size: 3.5rem; color: #aaa; cursor: pointer; line-height: 0 ; padding: 0; transition: color 0.2s ease; }
.close-button:hover { color: #333; }
.review-form { background-color: #f9f9f9; border: 1px solid #ccc; padding: 1.5rem; border-radius: 8px; margin-bottom: 2rem; }
.review-form h4 { margin-top: 0; text-align: center; }
.form-group { margin-bottom: 1rem; }
.form-group label { display: block; margin-bottom: 0.5rem; font-weight: 500; }
textarea { width: 100%; min-height: 80px; padding: 0.8rem; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; resize: vertical; }
.book-details h3 { color: #2c3e50; margin-top: 0; }
.year { color: #666; font-weight: normal; }
.author, .publish-house { color: #34495e; }
.rating-display { display: flex; align-items: center; gap: 0.5rem; margin: 1rem 0; }
.description { margin-top: 1rem; line-height: 1.6; }
.actions-section { border-top: 1px solid #eee; padding-top: 1.5rem; margin-top: 1.5rem; }
.comments-section { margin-top: 1.5rem; }
.comment { border: 1px solid #ccc; padding: 1rem; border-radius: 6px; margin-top: 1rem; }
.comment-header { display: flex; align-items: center; gap: 1rem; margin-bottom: 0.5rem; }
.comment-stars { font-size: 1rem; }
.comment-text { margin: 0; line-height: 1.5; }
.no-comments { color: #888; font-style: italic; }
button { width: 100%; padding: 0.8rem 1.5rem; background-color: #1e1e1e; color: white; border: 1px solid #444; border-radius: 4px; cursor: pointer; transition: background-color 0.2s; margin-top: 0.5rem; font-weight: 500; }
button:hover:not(:disabled) { background-color: #333; }
button:disabled { background-color: #555; color: #888; cursor: not-allowed; border-color: #555; }
</style>
