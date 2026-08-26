import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import FileUploadComponent from './components/FileUploadComponent';
import QueryComponent from './components/QueryComponent';


function App() {
  return (
    <div style={{ padding: '30px', fontFamily: 'Arial, sans-serif', maxWidth: '750px', margin: 'auto' }}>
      <h2>Health & Nutrition RAG System Dashboard</h2>
      
      {/* Task 2 Component 1: File Upload Section */}
      <FileUploadComponent />

      {/* Task 2 Component 2: Query Input Section */}
      <QueryComponent />
    </div>
  );
}



export default App
