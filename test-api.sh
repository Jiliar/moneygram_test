#!/bin/bash

# Book Catalog API Test Script
# This script tests all CRUD operations of the Book API

BASE_URL="http://localhost:8080/api/books"
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo "=========================================="
echo "Book Catalog API - Integration Tests"
echo "=========================================="
echo ""

# Test 1: Create a book
echo -e "${YELLOW}Test 1: Create a book (POST)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Don Quixote",
    "author": "Miguel de Cervantes",
    "isbn": "978-84-376-0494-7",
    "publishedYear": 1605,
    "genre": "Novel"
  }')

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 201 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 201 Created${NC}"
    BOOK_ID=$(echo $BODY | grep -o '"id":[0-9]*' | grep -o '[0-9]*')
    echo "Response: $BODY"
else
    echo -e "${RED}✗ FAILED - Expected: 201, Got: $HTTP_CODE${NC}"
fi
echo ""

# Test 2: Get all books
echo -e "${YELLOW}Test 2: Get all books (GET)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" $BASE_URL)
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 200 OK${NC}"
    echo "Response: $BODY"
else
    echo -e "${RED}✗ FAILED - Expected: 200, Got: $HTTP_CODE${NC}"
fi
echo ""

# Test 3: Get book by ID
echo -e "${YELLOW}Test 3: Get book by ID (GET)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" $BASE_URL/$BOOK_ID)
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 200 OK${NC}"
    echo "Response: $BODY"
else
    echo -e "${RED}✗ FAILED - Expected: 200, Got: $HTTP_CODE${NC}"
fi
echo ""

# Test 4: Update a book
echo -e "${YELLOW}Test 4: Update a book (PUT)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" -X PUT $BASE_URL/$BOOK_ID \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Don Quixote de la Mancha",
    "author": "Miguel de Cervantes Saavedra",
    "isbn": "978-84-376-0494-7",
    "publishedYear": 1605,
    "genre": "Classic Novel"
  }')

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
BODY=$(echo "$RESPONSE" | sed '$d')

if [ "$HTTP_CODE" -eq 200 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 200 OK${NC}"
    echo "Response: $BODY"
else
    echo -e "${RED}✗ FAILED - Expected: 200, Got: $HTTP_CODE${NC}"
fi
echo ""

# Test 5: Delete a book
echo -e "${YELLOW}Test 5: Delete a book (DELETE)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" -X DELETE $BASE_URL/$BOOK_ID)
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" -eq 204 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 204 No Content${NC}"
else
    echo -e "${RED}✗ FAILED - Expected: 204, Got: $HTTP_CODE${NC}"
fi
echo ""

# Test 6: Get deleted book (should return 404)
echo -e "${YELLOW}Test 6: Get deleted book - should return 404 (GET)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" $BASE_URL/$BOOK_ID)
HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" -eq 404 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 404 Not Found${NC}"
else
    echo -e "${RED}✗ FAILED - Expected: 404, Got: $HTTP_CODE${NC}"
fi
echo ""

# Test 7: Invalid request (should return 400)
echo -e "${YELLOW}Test 7: Invalid request - should return 400 (POST)${NC}"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "author": "",
    "isbn": "",
    "publishedYear": null,
    "genre": ""
  }')

HTTP_CODE=$(echo "$RESPONSE" | tail -n1)

if [ "$HTTP_CODE" -eq 400 ]; then
    echo -e "${GREEN}✓ PASSED - Status: 400 Bad Request${NC}"
else
    echo -e "${RED}✗ FAILED - Expected: 400, Got: $HTTP_CODE${NC}"
fi
echo ""

echo "=========================================="
echo "Tests completed"
echo "=========================================="
