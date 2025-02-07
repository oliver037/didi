const mongoose = require('mongoose');

const caseSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    carBrand: {
        type: String,
        required: true
    },
    carModel: {
        type: String,
        required: true
    },
    carCategory: {
        type: String,
        required: true
    },
    budget: {
        type: Number,
        required: true
    },
    rating: {
        type: Number,
        default: 0
    },
    images: [{
        type: String
    }],
    tags: [{
        type: String
    }],
    modifications: [{
        type: String
    }],
    author: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
        required: true
    },
    createdAt: {
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('Case', caseSchema); 