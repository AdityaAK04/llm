import React, { useState } from 'react';
import axios from 'axios';

function QueryComponent() {
  const [query, setQuery] = useState('');
  const [aiResponse, setAiResponse] = useState('');
  const [loading, setLoading] = useState(false);

  const handleQuerySubmit = async (e) => {
    e.preventDefault();
    if (!query.trim()) return;

    setLoading(true);
    setAiResponse('');

    try {
      const response = await axios.get('http://localhost:8080/rag/query', {
        params: { message: query }
      });
      setAiResponse(response.data);
    } catch (err) {
      setAiResponse('Error fetching response: ' + (err.response?.data || err.message));
    }
    setLoading(false);
  };

  return (
    <div style={{ border: '1px solid #ccc', padding: '20px', borderRadius: '8px' }}>
      <h3>Ask Questions (RAG Query)</h3>
      <form onSubmit={handleQuerySubmit}>
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="e.g., What are the daily serving recommendations?"
          style={{ width: '70%', padding: '8px' }}
        />
        <button type="submit" disabled={loading} style={{ marginLeft: '10px', padding: '8px 15px' }}>
          {loading ? 'Thinking...' : 'Ask AI'}
        </button>
      </form>

      <div style={{ marginTop: '20px', background: 'black', padding: '15px', borderRadius: '5px' }}>
        <h4>AI Response:</h4>
        <p>{loading ? 'Searching document context...' : aiResponse || 'Your answer will appear here.'}</p>
      </div>
    </div>
  );
}

export default QueryComponent;