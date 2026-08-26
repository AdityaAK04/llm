import React, { useState } from 'react';
import axios from 'axios';

function FileUploadComponent() {
  const [file, setFile] = useState(null);
  const [uploadStatus, setUploadStatus] = useState('');
  const [loading, setLoading] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUpload = async (e) => {
    e.preventDefault();
    if (!file) {
      alert('Please select a PDF file first!');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    setLoading(true);
    setUploadStatus('Uploading PDF and generating embeddings...');

    try {
      const response = await axios.post('http://localhost:8080/rag/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      setUploadStatus(response.data);
    } catch (err) {
      setUploadStatus('Upload failed: ' + (err.response?.data || err.message));
    }
    setLoading(false);
  };

  return (
    <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px', marginBottom: '20px' }}>
      <h3>Upload Health & Nutrition PDF</h3>
      <form onSubmit={handleUpload}>
        <input type="file" accept="application/pdf" onChange={handleFileChange} />
        <button type="submit" disabled={loading} style={{ marginLeft: '10px', padding: '6px 15px' }}>
          {loading ? 'Processing...' : 'Upload'}
        </button>
      </form>
      <p style={{ marginTop: '10px', fontWeight: 'bold' }}>{uploadStatus}</p>
    </div>
  );
}

export default FileUploadComponent;