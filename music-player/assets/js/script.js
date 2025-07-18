'use strict';

// Playlist array: update with your song paths/images
const songs = [
  { title:'Living Hope', artist:'Phil Wickham', src:'./assets/music/Living Hope.mp3', img:'./assets/images/Living Hope1.jpg' },
  { title:'Altar', artist:'Forest Blakk', src:'./assets/music/Altar.mp3', img:'./assets/images/altar.jpg' },
  { title:'Abraham Ka Prabhu', artist:'Amit Kamble', src:'./assets/music/AbrahamKaPrabhu.mp3', img:'./assets/images/AbrahamKaPrabhu.jpg' },
  { title:"That's Who I Praise", artist:'Brandon Lake', src:'./assets/music/Thats Who I Praise.mp3', img:'./assets/images/Thats_WhoIPraise.jpg' },
  { title:'Dhanyawad ke Saath', artist:'Sheldon Bangera', src:'./assets/music/Dhanyawad ke Saath.mp3', img:'./assets/images/DhanyawadKeSaath.jpg' }
];

// DOM Elements
const $ = q => document.querySelector(q);
const $$ = q => document.querySelectorAll(q);
const els = {
  banner: $('[data-player-banner]'),
  title: $('[data-title]'),
  album: $('[data-album]'),
  year: $('[data-year]'),
  artist: $('[data-artist]'),
  range: $$('input[data-range]'),
  seek: $('[data-seek]'),
  runningTime: $('[data-running-time]'),
  duration: $('[data-duration]'),
  playBtn: $('[data-play-btn]'),
  prevBtn: $('[data-skip-prev]'),
  nextBtn: $('[data-skip-next]'),
  shuffleBtn: $('[data-shuffle]'),
  repeatBtn: $('[data-repeat]'),
  volumeBtn: $('[data-volume-btn]'),
  volume: $('[data-volume]'),
  playlist: $('[data-music-list]'),
  playlistTogglers: $$('[data-playlist-toggler]'),
  overlay: $('[data-overlay]'),
  search: $('#searchInput'),
  favList: $('#favorites'),
  recentList: $('#recent'),

};

// Audio state
let currentIndex = 0, shuffled = false, repeating = false;
const audio = new Audio();

// LocalStorage state
const favs = JSON.parse(localStorage.getItem('favs') || '[]');
const recents = JSON.parse(localStorage.getItem('recents') || '[]');


// Build playlist UI
songs.forEach((s, i) => {
  const li = document.createElement('li');
  li.innerHTML = `
    <img src="${s.img}" alt="">
    <div class="info">${s.title} – ${s.artist}</div>
    <button data-i="${i}">${favs.includes(i) ? '★' : '☆'}</button>`;
  els.playlist.append(li);

  li.querySelector('.info').onclick = () => selectTrack(i);
  li.querySelector('button').onclick = e => toggleFavorite(e, i);
});

// Favorites UI
function renderFavs() {
  els.favList.innerHTML = '';
  favs.forEach(i => {
    const s = songs[i];
    const li = document.createElement('li');
    li.innerHTML = `<img src="${s.img}"><div class="info">${s.title} – ${s.artist}</div>`;
    li.onclick = () => selectTrack(i);
    els.favList.append(li);
  });
}

// Recents UI
function renderRecents() {
  els.recentList.innerHTML = '';
  // Filter out invalid indices that don't correspond to a song
  const validRecents = recents.filter(i => i >= 0 && i < songs.length);
  
  // Limit to last 10
  const limit = validRecents.length < 15 ? validRecents.length : 15;

  validRecents.slice(-limit).reverse().forEach(i => {
    const s = songs[i];
    const li = document.createElement('li');
    li.innerHTML = `<img src="${s.img}"><div class="info">${s.title} – ${s.artist}</div>`;
    li.onclick = () => selectTrack(i);
    els.recentList.append(li);
  });
}


// Helper: zero-padded time
function formatTime(sec) {
  const m = Math.floor(sec / 60), s = Math.floor(sec % 60);
  return `${m}:${s < 10 ? '0' : ''}${s}`;
}

// Load & play track
function selectTrack(i) {
  currentIndex = i;
  loadTrack(i);
  playTrack();
  saveRecent(i);
}
function loadTrack(i) {
  const s = songs[i];
  audio.src = s.src;
  els.banner.src = s.img;
  els.title.textContent = s.title;
  els.artist.textContent = s.artist;
  els.album.textContent = s.title;
  els.year.textContent = new Date().getFullYear();
  els.seek.value = 0;
}
function playTrack() {
  audio.play();
  els.playBtn.classList.add('active');
}
function pauseTrack() {
  audio.pause();
  els.playBtn.classList.remove('active');
}

// Add to recents
function saveRecent(i) {
  recents.push(i);
  localStorage.setItem('recents', JSON.stringify(recents));
  renderRecents();
}

// Favorite toggle
function toggleFavorite(e, i) {
  e.stopPropagation();
  if (favs.includes(i)) {
    favs.splice(favs.indexOf(i), 1);
  } else favs.push(i);
  localStorage.setItem('favs', JSON.stringify(favs));
  e.target.textContent = favs.includes(i) ? '★' : '☆';
  renderFavs();
}

// Event bindings
els.playBtn.onclick = () => audio.paused ? playTrack() : pauseTrack();
els.nextBtn.onclick = () => nextTrack();
els.prevBtn.onclick = () => prevTrack();

function nextTrack() {
  currentIndex = shuffled ? Math.floor(Math.random()*songs.length) : (currentIndex + 1) % songs.length;
  selectTrack(currentIndex);
}
function prevTrack() {
  currentIndex = shuffled ? Math.floor(Math.random()*songs.length) : (currentIndex - 1 + songs.length) % songs.length;
  selectTrack(currentIndex);
}

audio.ontimeupdate = () => {
  els.runningTime.textContent = formatTime(audio.currentTime);
  els.seek.value = audio.currentTime;
};

audio.onloadedmetadata = () => {
  els.duration.textContent = formatTime(audio.duration);
  els.seek.max = audio.duration;
};

audio.onended = () => {
  if (repeating) selectTrack(currentIndex); else nextTrack();
};

els.seek.oninput = () => audio.currentTime = els.seek.value;

els.volume.oninput = () => {
  audio.volume = els.volume.value;
  els.volumeBtn.querySelector('span').textContent = audio.muted ? '🔇' : (audio.volume > 0.5 ? '🔊' : '🔉');
};
els.volume.value = 1;

els.volumeBtn.onclick = () => {
  audio.muted = !audio.muted;
  els.volumeBtn.querySelector('span').textContent = audio.muted ? '🔇' : '🔊';
};

els.shuffleBtn.onclick = () => {
  shuffled = !shuffled;
  els.shuffleBtn.classList.toggle('active', shuffled);
};

els.repeatBtn.onclick = () => {
  repeating = !repeating;
  els.repeatBtn.classList.toggle('active', repeating);
};

// Playlist toggler
els.playlistTogglers.forEach(btn => btn.onclick = () => {
  $('.playlist').classList.toggle('active');
  els.overlay.classList.toggle('active');
  document.body.classList.toggle('modalActive');
});
els.overlay.onclick = () => {
  $('.playlist').classList.remove('active');
  els.overlay.classList.remove('active');
  document.body.classList.remove('modalActive');
};

// Search filter
els.search.oninput = () => {
  const q = els.search.value.toLowerCase();
  els.playlist.childNodes.forEach((li, i) => {
    const txt = `${songs[i].title} ${songs[i].artist}`.toLowerCase();
    li.style.display = txt.includes(q) ? 'flex' : 'none';
  });
};
const micBtn = document.getElementById('voiceSearchBtn');
const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

if (SpeechRecognition) {
  const recognition = new SpeechRecognition();
  recognition.continuous = false;
  recognition.lang = 'en-US';

  micBtn.onclick = () => {
    recognition.start();
    micBtn.textContent = '🎙️'; // Active mic icon
  };

  recognition.onresult = event => {
    let transcript = event.results[0][0].transcript.trim();
    transcript = transcript.replace(/[.,!?]+$/, ''); // Remove punctuation at end
    els.search.value = transcript;
    micBtn.textContent = '🎤';
    els.search.dispatchEvent(new Event('input')); // Trigger your search
  };
  

  recognition.onend = () => {
    micBtn.textContent = '🎤';
  };

  if (!('webkitSpeechRecognition' in window)) {
    showToast('Voice search not supported in this browser.');
  }
  
} else {
  micBtn.style.display = 'none';
  console.warn('Speech Recognition not supported in this browser.');
}

function showToast(message) {
  const toast = document.getElementById('toast');
  toast.textContent = message;
  toast.classList.add('show');

  setTimeout(() => {
    toast.classList.remove('show');
  }, 3000);
}


// Initialize view
renderFavs();
renderRecents();
selectTrack(0);
