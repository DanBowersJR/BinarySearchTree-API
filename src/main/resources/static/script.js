// ------------------- BST CLASS -------------------
class Node {
    constructor(value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

class BST {
    constructor() {
        this.root = null;
    }

    insert(value) {
        const newNode = new Node(value);
        if (!this.root) {
            this.root = newNode;
            return;
        }
        let current = this.root;
        while (true) {
            if (value === current.value) {
                return; // Prevent duplicates
            } else if (value < current.value) {
                if (!current.left) {
                    current.left = newNode;
                    return;
                }
                current = current.left;
            } else {
                if (!current.right) {
                    current.right = newNode;
                    return;
                }
                current = current.right;
            }
        }
    }

    toJSON() {
        return JSON.stringify(this.root, null, 2);
    }
}

// ------------------- GLOBALS -------------------
let bst = null;
let previousTrees = [];

// ------------------- FUNCTIONS -------------------
function submitNumbers() {
    const input = document.getElementById("numbersInput").value.trim();

    if (!input) {
        alert("⚠️ Please enter valid numbers!");
        return;
    }

    const numbers = input.split(/[\s,]+/)
        .map(n => parseInt(n.trim()))
        .filter(n => !isNaN(n));

    if (numbers.length === 0) {
        alert("⚠️ Please enter valid numbers!");
        return;
    }

    bst = new BST();
    numbers.forEach(num => bst.insert(num));

    previousTrees.push(bst.toJSON());

    document.getElementById("resultBox").textContent = bst.toJSON();

    drawTree(bst.root);
}

function showPrevious() {
    if (previousTrees.length === 0) {
        alert("⚠️ No previous trees found!");
        return;
    }

    document.getElementById("resultBox").textContent = previousTrees
        .map((tree, idx) => `Tree #${idx + 1}:\n${tree}`)
        .join("\n\n--- PREVIOUS TREE ---\n\n");
}

function clearTree() {
    bst = null;
    document.getElementById("numbersInput").value = "";
    document.getElementById("resultBox").textContent = "";
    const canvas = document.getElementById("treeCanvas");
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

// ------------------- DRAW TREE -------------------
function drawTree(root) {
    const canvas = document.getElementById("treeCanvas");
    const ctx = canvas.getContext("2d");

    // Resize canvas
    canvas.width = canvas.clientWidth;
    canvas.height = 500;

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    if (!root) return;

    const levelGap = 80;
    const nodeRadius = 22;

    function drawNode(node, x, y, depth) {
        if (!node) return;

        const baseSpacing = 70;
        const offset = Math.max(baseSpacing, canvas.width / (Math.pow(2, depth + 2)));

        ctx.shadowBlur = 0;

        if (node.left) {
            ctx.beginPath();
            ctx.moveTo(x, y);
            ctx.lineTo(x - offset, y + levelGap);
            ctx.strokeStyle = "#2ecc71";
            ctx.lineWidth = 2;
            ctx.stroke();
            drawNode(node.left, x - offset, y + levelGap, depth + 1);
        }

        if (node.right) {
            ctx.beginPath();
            ctx.moveTo(x, y);
            ctx.lineTo(x + offset, y + levelGap);
            ctx.strokeStyle = "#2ecc71";
            ctx.lineWidth = 2;
            ctx.stroke();
            drawNode(node.right, x + offset, y + levelGap, depth + 1);
        }

        // Node circle
        ctx.beginPath();
        ctx.arc(x, y, nodeRadius, 0, 2 * Math.PI);
        ctx.fillStyle = "#2ecc71";
        ctx.shadowBlur = 12;
        ctx.shadowColor = "#00ff99";
        ctx.fill();
        ctx.lineWidth = 2;
        ctx.strokeStyle = "#0f2027";
        ctx.stroke();

        // Node text
        ctx.fillStyle = "#000";
        ctx.font = "bold 15px Courier New";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(node.value, x, y);
    }

    drawNode(root, canvas.width / 2, 60, 0);
}

// ------------------- MATRIX BACKGROUND -------------------
const bgCanvas = document.createElement("canvas");
bgCanvas.id = "matrixBackground";
bgCanvas.style.position = "fixed";
bgCanvas.style.top = "0";
bgCanvas.style.left = "0";
bgCanvas.style.width = "100%";
bgCanvas.style.height = "100%";
bgCanvas.style.zIndex = "-1";
document.body.appendChild(bgCanvas);

const bgCtx = bgCanvas.getContext("2d");
let fontSize = 16;
let columns;
let drops;

function initMatrix() {
    bgCanvas.width = window.innerWidth;
    bgCanvas.height = window.innerHeight;

    columns = Math.floor(bgCanvas.width / fontSize);
    drops = Array(columns).fill(1);
}

function drawMatrix() {
    bgCtx.fillStyle = "rgba(0, 0, 0, 0.08)";
    bgCtx.fillRect(0, 0, bgCanvas.width, bgCanvas.height);

    bgCtx.fillStyle = "#0F0";
    bgCtx.font = fontSize + "px monospace";

    for (let i = 0; i < drops.length; i++) {
        const text = String.fromCharCode(0x30A0 + Math.random() * 96);
        bgCtx.fillText(text, i * fontSize, drops[i] * fontSize);

        if (drops[i] * fontSize > bgCanvas.height && Math.random() > 0.975) {
            drops[i] = 0;
        }
        drops[i]++;
    }
}

initMatrix();
setInterval(drawMatrix, 50);
window.addEventListener("resize", initMatrix);
