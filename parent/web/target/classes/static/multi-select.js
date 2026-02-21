class MultiSelect extends HTMLElement {
  constructor() {
    super()
    this.attachShadow({ mode: 'open' })

    this.selected = new Set()
    this.options = []
  }

  static get observedAttributes() {
    return ['options']
  }

  attributeChangedCallback(name, _, newValue) {
    if (name === 'options') {
      try {
        this.options = JSON.parse(newValue) || []
        this.render()
      } catch {
        this.options = []
      }
    }
  }

  connectedCallback() {
    if (this.hasAttribute('options')) {
      this.options = JSON.parse(this.getAttribute('options'))
    }
    this.render()
  }

  toggleValue(value) {
    if (this.selected.has(value)) {
      this.selected.delete(value)
    } else {
      this.selected.add(value)
    }

    this.dispatchEvent(
        new CustomEvent('change', {
          detail: Array.from(this.selected),
        })
    )

    this.render()
  }

  render() {
    this.shadowRoot.innerHTML = `
      <style>
        :host {
          display: block;
          font-family: system-ui, sans-serif;
          width: 260px;
        }

        .container {
          border: 1px solid #ddd;
          border-radius: 10px;
          padding: 8px;
          background: white;
        }

        .option {
          display: flex;
          align-items: center;
          gap: 8px;
          padding: 6px;
          border-radius: 6px;
          cursor: pointer;
        }

        .option:hover {
          background: #f1f5f9;
        }

        input {
          cursor: pointer;
        }
      </style>

      <div class="container">
        ${this.options
        .map(
            option => `
              <label class="option">
                <input
                  type="checkbox"
                  ${this.selected.has(option.value) ? 'checked' : ''}
                  data-value="${option.value}"
                />
                <span>${option.label}</span>
              </label>
            `
        )
        .join('')}
      </div>
    `

    this.shadowRoot
        .querySelectorAll('input[type="checkbox"]')
        .forEach(input => {
          input.addEventListener('change', e => {
            this.toggleValue(e.target.dataset.value)
          })
        })
  }

  get value() {
    return Array.from(this.selected)
  }
}

customElements.define('multi-select', MultiSelect)